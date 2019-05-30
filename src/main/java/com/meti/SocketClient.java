package com.meti;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class SocketClient implements Client {
    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    SocketClient(int port) throws IOException {
        this(InetAddress.getLocalHost(), port);
    }

    private SocketClient(InetAddress address, int port) throws IOException {
        this(new Socket(address, port));
    }

    private SocketClient(Socket socket) throws IOException {
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
        processResponse(inputStream.readObject());
    }

    private void processResponse(Object response) throws Throwable {
        if (response instanceof Throwable) {
            throw (Throwable) response;
        } else {
            processResponseNonThrowable(response);
        }
    }

    private final Set<ResponseHandler> handlers = new HashSet<>();

    @Override
    public Set<ResponseHandler> getHandlers() {
        return handlers;
    }

    private void processResponseNonThrowable(Object response) {
        boolean noHandlerFound = !processWithHandlers(response)
                .findAny()
                .isPresent();
        if (noHandlerFound) {
            throw new UnsupportedOperationException("Unknown response: " + response);
        }
    }

    private Stream<ResponseHandler> processWithHandlers(Object response) {
        return handlers.stream()
                .filter(handler -> handler.canHandle(response))
                .peek(handler -> handler.handle(response, this));
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