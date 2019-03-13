package com.meti.lib;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Optional;
import java.util.concurrent.Callable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/12/2019
 */
public abstract class Client implements Closeable, Callable<Optional<Exception>> {
    public final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    public Client(Socket socket) throws IOException {
        this.socket = socket;

        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
    }

    public void close() throws IOException {
        socket.close();

        inputStream.close();
        outputStream.close();
    }

    @Override
    public Optional<Exception> call() {
        try {
            while (!socket.isClosed()) {
                handleObject(readObject());
            }

            return Optional.empty();
        } catch (IOException | ClassNotFoundException e) {
            if(e instanceof SocketException){
                return Optional.empty();
            }

            return Optional.of(e);
        }
    }

    protected abstract void handleObject(Object token);

    public void flush() throws IOException {
        outputStream.flush();
    }

    public Object readObject() throws IOException, ClassNotFoundException {
        return inputStream.readObject();
    }

    public void writeObject(Object obj) throws IOException {
        outputStream.writeObject(obj);
    }

    public void writeUnshared(Object obj) throws IOException {
        outputStream.writeUnshared(obj);
    }
}