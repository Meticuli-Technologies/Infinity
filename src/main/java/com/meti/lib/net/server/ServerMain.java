package com.meti.lib.net.server;

import com.meti.lib.net.handle.ResponseHandler;
import com.meti.lib.net.client.Client;
import com.meti.lib.net.client.SocketClient;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public class ServerMain {
    private final ExecutorService service = Executors.newCachedThreadPool();
    private final Scanner scanner = new Scanner(System.in);
    private int port;

    public static void main(String[] args) {
        ServerMain serverMain = new ServerMain();
        serverMain.init();
        serverMain.start();
        serverMain.run();
    }

    private void init() {
        System.out.print("Enter in a port, or 0 for a local one: ");
        port = scanner.nextInt();
    }

    private ServerSocket serverSocket;
    private boolean shouldContinue = true;

    private void run() {
        while (shouldContinue()) {
            loop();
        }

        stop();
    }

    private boolean shouldContinue() {
        return shouldContinue;
    }

    private void loop() {
        String input = scanner.nextLine();
        if (input.equals("exit")) {
            shouldContinue = false;
        }
    }

    private void stop() {
        try {
            serverSocket.close();
            scanner.close();

            service.shutdownNow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void start() {
        try {
            buildServerSocket(port);

            Set<ResponseHandler> handlers = Set.of(new StringResponseHandler());
            service.submit(new ClientAcceptor(handlers));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buildServerSocket(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Launched server on port " + serverSocket.getLocalPort() + '.');
    }

    private static class StringResponseHandler implements ResponseHandler {
        @Override
        public boolean canHandle(Object response) {
            return response instanceof String;
        }

        @Override
        public Optional<Serializable> handle(Object response, Client client) {
            return Optional.of(client.getName() + ": " + response);
        }
    }

    private static class ClientHandler implements Callable<Void> {
        private final Client client;

        ClientHandler(Client client) {
            this.client = client;
        }

        @Override
        public Void call() throws Exception {
            while (client.isOpen()) {
                try {
                    client.processNextResponse();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
            client.close();
            return null;
        }
    }

    private class ClientAcceptor implements Callable<Void> {
        private final Set<? extends ResponseHandler> handlers;

        ClientAcceptor(Set<? extends ResponseHandler> handlers) {
            this.handlers = handlers;
        }

        @Override
        public Void call() throws Exception {
            while (ServerMain.this.shouldContinue()) {
                Client client = acceptClient();
                submitClient(client);
            }
            return null;
        }

        private Client acceptClient() throws IOException {
            Socket acceptedSocket = serverSocket.accept();
            return new SocketClient(acceptedSocket);
        }

        private void submitClient(Client client) {
            client.getHandlers().addAll(handlers);
            service.submit(new ClientHandler(client));
        }
    }
}
