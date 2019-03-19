package com.meti.lib.net;

import com.meti.lib.respond.CachedResponse;

import java.io.*;
import java.net.Socket;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

public class Client implements Closeable {
    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    private final Queue<Object> tokens = new LinkedList<>();

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

    public Object read() throws IOException, ClassNotFoundException {
        return inputStream.readObject();
    }

    public <T> T read(Class<T> tClass) throws Exception {
        for(Object token : tokens){
            if(tClass.isAssignableFrom(token.getClass())){
                return tClass.cast(token);
            }
        }

        Object token = read();
        try {
            return tClass.cast(token);
        } catch (ClassCastException e){
            if(token instanceof CachedResponse<?>){
                ((CachedResponse) token).check();
            }

            tokens.add(token);

            throw e;
        }
    }

    public void write(Serializable serializable) throws IOException {
        outputStream.writeObject(serializable);
        outputStream.flush();
    }

    public void write(Collection<? extends Serializable> serializables) throws IOException {
        for (Serializable serializable : serializables) {
            outputStream.writeObject(serializable);
        }

        outputStream.flush();
    }
}