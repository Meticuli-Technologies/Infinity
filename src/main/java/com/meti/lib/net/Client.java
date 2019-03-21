package com.meti.lib.net;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client implements Closeable {
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;
    public final Socket socket;

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

    public <T> T queryObject(Object object, Class<T> returnClass) throws IOException, ClassNotFoundException {
        writeObject(object);

        return returnClass.cast(readObject());
    }

    private Object readObject() throws IOException, ClassNotFoundException {
        return inputStream.readObject();
    }

    public void writeObject(Object obj) throws IOException {
        outputStream.writeObject(obj);
    }

    public Object readUnshared() throws IOException, ClassNotFoundException {
        return inputStream.readUnshared();
    }

    public void writeUnshared(Object obj) throws IOException {
        outputStream.writeUnshared(obj);
    }
}