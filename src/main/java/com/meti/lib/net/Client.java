package com.meti.lib.net;

import com.meti.lib.util.QueuedFunction;
import com.meti.lib.util.TypePredicate;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.Callable;
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
    public void close() throws IOException {
        inputStream.close();
        outputStream.close();
        socket.close();
    }

    @Override
    public Void call() throws Exception {
        while (!Thread.interrupted() && !socket.isClosed()) {
            process(inputStream.readObject());
        }
        return null;
    }

    public <R> R query(Query<R> query) throws IOException {
        write(Collections.singleton(query));
        Class<R> returnType = query.returnType;

        if (!queryMap.containsKey(returnType)) {
            QueuedFunction<Object> function = new QueuedFunction<>();
            handlers.add(new AbstractTokenHandler<>(new TypePredicate<>(returnType), function));
            queryMap.put(returnType, function);
        }

        Queue<Object> queue = queryMap.get(returnType).queue;
        Object token;
        do {
            token = queue.poll();
        } while (token == null || !returnType.isAssignableFrom(token.getClass()));
        return returnType.cast(token);
    }

    private void write(Collection<?> objects) throws IOException {
        for (Object object : objects) {
            outputStream.writeObject(object);
        }

        outputStream.flush();
    }

    private void process(Object token) throws IOException {
        List<Object> outputs = getOutputs(token);
        if (outputs.size() == 0) {
            throw new IllegalArgumentException("Could not process " + token);
        }

        check(token, outputs);
        write(outputs);
    }

    private List<Object> getOutputs(Object token) {
        return handlers.stream()
                .filter(objectTokenHandler -> objectTokenHandler.test(token))
                .map((Function<TokenHandler<Object, ?>, Object>) objectTokenHandler -> objectTokenHandler.apply(token))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private void check(Object token, Object output) {
        if (token instanceof Query<?>) {
            Query<?> query = (Query<?>) token;
            if (!query.check(output.getClass())) {
                throw new IllegalArgumentException("Type " + output.getClass() + " does not match " + query.returnType);
            }
        }
    }
}