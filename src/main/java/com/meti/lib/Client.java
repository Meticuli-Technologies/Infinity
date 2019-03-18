package com.meti.lib;

import java.io.*;
import java.net.Socket;
import java.time.Duration;
import java.util.Collection;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class Client implements Closeable {
    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;
    public static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);

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

    public Socket getSocket() {
        if (socket == null) {
            throw new IllegalStateException("Socket has not been set!");
        }

        return socket;
    }

    public <R extends Response> R query(Respondable<R> respondable) throws Exception {
        return query(respondable, DEFAULT_TIMEOUT);
    }

    private <R extends Response> R query(Respondable<R> respondable, Duration timeout) throws Exception {
        write(respondable);

        Class<? extends R> responseClass = respondable.getResponseClass();

        Object token = read(timeout);
        if (responseClass.isAssignableFrom(token.getClass())) {
            return responseClass.cast(token);
        } else {
            throw new IllegalStateException("Invalid token: " + token);
        }
    }

    public void write(Serializable serializable) throws IOException {
        outputStream.writeObject(serializable);
        outputStream.flush();
    }

    public void write(Collection<? extends Serializable> serializables) throws IOException {
        for(Serializable serializable : serializables){
            outputStream.writeObject(serializable);
        }

        outputStream.flush();
    }

    public Object read() throws Exception {
        return read(DEFAULT_TIMEOUT);
    }

    private Object read(Duration timeout) throws Exception {
        return new FutureTask<>(inputStream::readObject).get(timeout.toMillis(), TimeUnit.MILLISECONDS);
    }
}