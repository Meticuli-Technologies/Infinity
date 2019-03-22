package com.meti.lib.net;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client implements Closeable {
    public final Socket socket;

    private final ExecutorService service = Executors.newSingleThreadExecutor();
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    public Client(Socket socket) throws IOException {
        this.socket = socket;
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void close() throws IOException {
        service.shutdownNow();

        inputStream.close();
        outputStream.close();
        socket.close();
    }

    public <T> T queryObject(Object object, Class<T> returnClass) throws Exception {
        return returnClass.cast(service.submit(() -> {
            writeUnshared(object);
            return readUnshared();
        }).get());
    }

    public void writeUnshared(Object obj) throws IOException {
        outputStream.writeUnshared(obj);
    }

    public Object readUnshared() throws IOException, ClassNotFoundException {
        return inputStream.readUnshared();
    }

    private Object readObject() throws IOException, ClassNotFoundException {
        return inputStream.readObject();
    }

    public void writeObject(Object obj) throws IOException {
        outputStream.writeObject(obj);
    }
}