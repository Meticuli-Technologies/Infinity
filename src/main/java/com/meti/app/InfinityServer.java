package com.meti.app;

import com.meti.lib.Client;
import com.meti.lib.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class InfinityServer extends Server {
    private final ExecutorService service = Executors.newCachedThreadPool();

    public InfinityServer(int port) throws IOException {
        super(new ServerSocket(port));
    }

    @Override
    public void handleAccept(Socket accept) throws Exception {
        System.out.println("Located client at " + accept.getInetAddress());
        service.submit(new ClientHandler(new Client(accept)));
    }

    private class ClientHandler implements Callable<Void> {
        private Client client;

        public ClientHandler(Client client) {
            this.client = client;
        }

        @Override
        public Void call() throws IOException {
            System.out.println("Handling client at " + client.getSocket().getInetAddress());
            client.close();
            return null;
        }
    }
}
