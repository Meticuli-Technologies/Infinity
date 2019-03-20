package com.meti.lib.net;

import com.meti.lib.util.CollectionUtil;
import com.meti.lib.util.QueuedFunction;
import com.meti.lib.util.TypePredicate;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Client implements Callable<Void>, Closeable {
    public final Set<TokenHandler<Object, ?>> handlers = new HashSet<>();

    private final Map<Class<?>, QueuedFunction<Object>> queryMap = new HashMap<>();
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;
    private final Socket socket;

    public Client(Socket socket) throws IOException {
        this.socket = socket;
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public Void call() {
        while (true) {
            try {
                process(inputStream.readObject());
            } catch (Exception e) {
                if (e instanceof SocketException) {
                    break;
                } else {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private void process(Object token) throws IOException {
        Optional<Object> output = getOutputs(token);
        if (!output.isPresent()) {
            throw new IllegalArgumentException("Could not process " + token);
        } else {
            check(token, output.get());
            write(output.get());
        }
    }

    private void write(Object objects) throws IOException {
        write(Collections.singletonList(objects));
    }

    private void write(Collection<?> objects) throws IOException {
        for (Object object : objects) {
            outputStream.writeObject(object);
        }

        outputStream.flush();
    }

    private Optional<Object> getOutputs(Object token) {
        return CollectionUtil.toSingle(handlers.stream()
                .filter(objectTokenHandler -> objectTokenHandler.test(token))
                .peek(Object::notify)
                .map((Function<TokenHandler<Object, ?>, Object>) objectTokenHandler -> objectTokenHandler.apply(token))
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
    }

    private void check(Object token, Object output) {
        if (token instanceof Query<?>) {
            Query<?> query = (Query<?>) token;
            if (!query.check(output.getClass())) {
                throw new IllegalArgumentException("Type " + output.getClass() + " does not match " + query.returnType);
            }
        }
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
        outputStream.close();
        socket.close();
    }

    public <R> R query(Query<R> query) throws IOException, InterruptedException {
        write(Collections.singleton(query));
        Class<R> returnType = query.returnType;

        if (!queryMap.containsKey(returnType)) {
            QueuedFunction<Object> function = new QueuedFunction<>();
            handlers.add(new AbstractTokenHandler<>(new TypePredicate<>(returnType), function));
            queryMap.put(returnType, function);
        }

        QueuedFunction<Object> function = queryMap.get(returnType);
        function.wait();

        Queue<Object> queue = queryMap.get(returnType).queue;
        if (queue.isEmpty()) {
            throw new IllegalStateException("Query is empty");
        }

        Object token;
        do {
            token = queue.poll();
        } while (token == null || !returnType.isAssignableFrom(token.getClass()));
        return returnType.cast(token);
    }
}