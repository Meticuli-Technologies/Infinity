package com.meti.app;

import com.meti.lib.Client;
import com.meti.lib.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
        private final Map<Predicate<Object>, Consumer<Object>> map = new HashMap<>();
        private Client client;


        public ClientHandler(Client client) {
            this.client = client;
        }

        @Override
        public Void call() throws IOException {
            System.out.println("Handling client at " + client.getSocket().getInetAddress());

            while (!Thread.interrupted() || !client.getSocket().isClosed()) {
                try {
                    Object token = client.read();
                    processToken(token);
                } catch (Exception e) {
                    client.write(e);
                }
            }

            if (!client.getSocket().isClosed()) {
                client.close();
            }
            return null;
        }

        private void processToken(Object token) {
            Set<Consumer<Object>> set = map.keySet().stream()
                    .filter(objectPredicate -> objectPredicate.test(token))
                    .map(map::get)
                    .peek(objectConsumer -> objectConsumer.accept(token))
                    .collect(Collectors.toSet());

            if (set.isEmpty()) {
                throw new IllegalStateException("Invalid token: " + token);
            }
        }
    }
}
