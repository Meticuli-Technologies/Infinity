package com.meti.app.client;

import com.meti.lib.net.client.Client;
import com.meti.lib.net.client.SocketClient;
import com.meti.lib.net.handle.ResponseHandler;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Supplier;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public class ClientMain {
    private Client client;
    private Scanner scanner;

    public static void main(String[] args) {
        ClientMain clientMain = new ClientMain();
        clientMain.start();
        clientMain.run();
    }

    private boolean isNotExitMessage(String message) {
        return !message.equals("exit");
    }

    private void loop(Supplier<String> supplier) {
        if (isNotExitMessage(supplier.get())) {
            writeMessage(supplier.get());
            loop(supplier);
        }
    }

    private void run() {
        loop(() -> scanner.nextLine());
        stop();
    }

    private void writeMessage(Serializable message) {
        try {
            client.writeAndFlush(message);
            client.processNextResponse();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void start() {
        try {
            scanner = new Scanner(System.in);
            client = new SocketClient(getPort());
            client.getHandlers().add(new StringResponsePrinter());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getPort() {
        System.out.print("Enter in the local port: ");
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid port, please try again.");
            }
        }
    }

    private void stop() {
        try {
            client.close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class StringResponsePrinter implements ResponseHandler {
        @Override
        public boolean canHandle(Object response) {
            return response instanceof String;
        }

        @Override
        public Optional<Serializable> handle(Object response, Client client) {
            System.out.println(response);
            return Optional.empty();
        }
    }
}
