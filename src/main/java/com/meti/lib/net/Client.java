package com.meti.lib.net;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client implements Closeable {
    public final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

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

    public <T> T queryObject(Object object, Class<T> returnClass) throws Exception {
        writeUnshared(object);
        return returnClass.cast(readUnshared());
    }

    public Object readUnshared() throws IOException, ClassNotFoundException {
        return inputStream.readUnshared();
    }

    public void writeUnshared(Object obj) throws IOException {
        outputStream.writeUnshared(obj);
    }

    private Object readObject() throws IOException, ClassNotFoundException {
        return inputStream.readObject();
    }

    public void writeObject(Object obj) throws IOException {
        outputStream.writeObject(obj);
    }
}