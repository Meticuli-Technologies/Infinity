package com.meti.lib.net;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Callable;

public class Client implements Callable<Void>, Closeable {
    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    private final Queue<Object> queue = new LinkedList<>();
    private boolean running;

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
        running = true;
        while (!Thread.interrupted() && !socket.isClosed()) {
            queue.add(inputStream.readObject());
        }
        return null;
    }

    public Object read() {
        if (!running) {
            throw new IllegalStateException("Client is not running");
        }

        return queue.poll();
    }

    public void write(Serializable... serializables) throws IOException {
        for (Serializable serializable : serializables) {
            outputStream.writeObject(serializable);
        }

        outputStream.flush();
    }
}