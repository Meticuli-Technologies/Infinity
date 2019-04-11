package com.meti;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class Client {
    final ObjectOutputStream objectOutputStream;
    final ObjectInputStream objectInputStream;
    public final Socket socket;

    public Client(Socket socket) throws IOException {
        this.socket = socket;
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    public void flush() throws IOException {
        objectOutputStream.flush();
    }

    public Object readObject() throws IOException, ClassNotFoundException {
        return objectInputStream.readObject();
    }

    public Object readUnshared() throws IOException, ClassNotFoundException {
        return objectInputStream.readUnshared();
    }

    public void writeObject(Object obj) throws IOException {
        objectOutputStream.writeObject(obj);
    }

    public void writeUnshared(Object obj) throws IOException {
        objectOutputStream.writeUnshared(obj);
    }

    public boolean isClosed() {
        return socket.isClosed();
    }
}
