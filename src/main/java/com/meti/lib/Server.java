package com.meti.lib;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public abstract class Server {
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void listen(Consumer<Runnable> runnableConsumer, Consumer<Exception> callback){
        runnableConsumer.accept(() -> {
            while(!serverSocket.isClosed()){
                try {
                    Socket socket = serverSocket.accept();
                    runnableConsumer.accept(handle(socket));
                } catch (IOException e) {
                    callback.accept(e);
                }
            }
        });
    }

    protected abstract Runnable handle(Socket socket);
}