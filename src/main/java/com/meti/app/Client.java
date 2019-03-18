package com.meti.app;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    Socket socket;
    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;

    public Client(Socket socket, ObjectInputStream inputStream, ObjectOutputStream outputStream) {
        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public Socket getSocket() {
        if (socket == null) {
            throw new IllegalStateException("Socket has not been set!");
        }

        return socket;
    }
}