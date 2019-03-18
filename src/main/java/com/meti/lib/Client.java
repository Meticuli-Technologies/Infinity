package com.meti.lib;

import java.io.*;
import java.net.Socket;
import java.time.Duration;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class Client implements Closeable {
    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    private final Queue<Object> buffer = new LinkedList<>();

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
        write(respondable);

        return read(respondable.getResponseClass());
    }

    public <T> T read(Class<T> tClass) throws Exception {
        Object token;
        if (buffer.stream().anyMatch(new TypePredicate<>(tClass))) {
            token = buffer.poll();
        } else {
            token = read();
        }
        return tClass.cast(token);
    }

    public Object read() throws Exception {
        Duration timeout = Duration.ofSeconds(1);
        return read(timeout);
    }

    private Object read(Duration timeout) throws Exception {
        FutureTask<Object> task = new FutureTask<>(inputStream::readObject);
        Object o = task.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
        if(o instanceof Exception) throw (Exception) o;
        buffer.add(o);
        return o;
    }

    public void write(Serializable serializable) throws Exception {
        outputStream.writeObject(serializable);
        outputStream.flush();

        read();
    }

    public void write(Collection<? extends Serializable> serializables) throws IOException {
        for (Serializable serializable : serializables) {
            outputStream.writeObject(serializable);
        }

        outputStream.flush();
    }
}