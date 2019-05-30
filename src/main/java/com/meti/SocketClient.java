package com.meti;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Set;

public class SocketClient implements Client {
    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;
    private final HandlerManager handlerManager = new SetBasedHandlerManager();

    SocketClient(int port) throws IOException {
        this(InetAddress.getLocalHost(), port);
    }

    private SocketClient(InetAddress address, int port) throws IOException {
        this(new Socket(address, port));
    }

    public SocketClient(Socket socket) throws IOException {
        this.socket = socket;
    /*
            The OOS must be constructed before the OIS because of the header.
             */
        this.outputStream = new ObjectOutputStream(this.socket.getOutputStream());
        this.inputStream = new ObjectInputStream(this.socket.getInputStream());
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }

    @Override
    public void processNextResponse() throws Throwable {
        Object nextResponse = inputStream.readObject();
        Set<Serializable> serializablesToWrite = handlerManager.processResponse(nextResponse);
        writeAndFlushIterable(serializablesToWrite);
    }

    @Override
    public void writeAndFlushIterable(Iterable<? extends Serializable> collection) throws IOException {
        for (Serializable serializable : collection) {
            write(serializable);
        }
        flush();
    }

    @Override
    public Set<ResponseHandler> getHandlers() {
        return handlerManager.getHandlers();
    }

    @Override
    public void write(Serializable serializable) throws IOException {
        outputStream.writeObject(serializable);
    }

    @Override
    public void flush() throws IOException {
        outputStream.flush();
    }
}