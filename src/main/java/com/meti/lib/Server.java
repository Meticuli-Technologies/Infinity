package com.meti.lib;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

public abstract class Server implements Callable<Optional<Exception>> {
    private final List<Socket> socketList = new ArrayList<>();
    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public Optional<Exception> call() {
        try {
            while(!serverSocket.isClosed()){
                Socket accept = serverSocket.accept();
                socketList.add(accept);
                handleAccept(accept);
            }
            return Optional.empty();
        } catch (Exception e) {
            return Optional.of(e);
        }
    }

    public abstract void handleAccept(Socket accept) throws Exception;
}
