package com.meti.lib.net;

import java.io.*;
import java.net.Socket;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
            Object token = inputStream.readObject();
            List<Serializable> toWrite = handlers.stream()
                    .filter(objectTokenHandler -> objectTokenHandler.test(token))
                    .map((Function<TokenHandler<Object, ? extends Serializable>, Serializable>) objectTokenHandler -> objectTokenHandler.apply(token))
                    .collect(Collectors.toList());
            write(toWrite);
        }
        return null;
    }

    public void write(Collection<? extends Serializable> serializables) throws IOException {
        for (Serializable serializable : serializables) {
            outputStream.writeObject(serializable);
        }

        outputStream.flush();
    }
}