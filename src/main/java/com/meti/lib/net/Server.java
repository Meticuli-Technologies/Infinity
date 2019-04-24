package com.meti.lib.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;
import java.util.function.Predicate;

public class Server {
    private final Map<Predicate<Object>, Function<Object, Object>> resultMapper = new HashMap<>();
    private final ServerSocket serverSocket;

    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public void listen(ExecutorService service) {
        service.submit(new ClientBuilder(service));
    }

    private class ClientBuilder implements Callable<Void> {
        private final ExecutorService service;

        ClientBuilder(ExecutorService service) {
            this.service = service;
        }

        @Override
        public Void call() throws Exception {
            while (!serverSocket.isClosed()) {
                Client client = new Client(serverSocket.accept());
                service.submit(new Processor(client, resultMapper));
            }
            return null;
        }
    }
}
