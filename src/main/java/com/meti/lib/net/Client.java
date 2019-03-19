package com.meti.lib.net;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Client implements Callable<Void>, Closeable {
    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    private final Set<TokenHandler<Object, ?>> handlers = new HashSet<>();

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

    private void process(Object token) throws IOException {
        List<Serializable> outputs = getOutputs(token);
        check(token, outputs);
        write(outputs);
    }

    private List<Serializable> getOutputs(Object token) {
        return handlers.stream()
                .filter(objectTokenHandler -> objectTokenHandler.test(token))
                .map((Function<TokenHandler<Object, ? extends Serializable>, Serializable>) objectTokenHandler -> objectTokenHandler.apply(token))
                .collect(Collectors.toList());
    }

    private void check(Object token, List<Serializable> outputs) {
        if (token instanceof Query) {
            Class<?>[] others = outputs.stream()
                    .map(Serializable::getClass)
                    .toArray(value -> new Class<?>[0]);
            Query query = (Query) token;
            if (!query.check(others)) {
                throw new IllegalArgumentException("Expected outputs to contain " + Arrays.toString(query.classes) + ",\n" +
                        "but found instead " + Arrays.toString(others));
            }
        }
    }

    private void write(Collection<? extends Serializable> serializables) throws IOException {
        for (Serializable serializable : serializables) {
            outputStream.writeObject(serializable);
        }

        outputStream.flush();
    }
}