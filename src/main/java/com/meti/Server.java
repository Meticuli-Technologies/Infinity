package com.meti;

import java.net.ServerSocket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
class Server implements Callable<Void> {
    private final ServerSocket serverSocket;
    private final ExecutorService service;

    public Server(ServerSocket serverSocket, ExecutorService service) {
        this.serverSocket = serverSocket;
        this.service = service;


        //TODO: add handlers
    }

    @Override
    public Void call() throws Exception {
        while (!serverSocket.isClosed()) {
            Client client = new Client(serverSocket.accept());
            service.submit(new TokenHandler(client));
        }
        return null;
    }

}
