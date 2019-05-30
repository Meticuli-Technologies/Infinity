package com.meti;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;
import java.util.function.Supplier;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public class ClientMain {
    private SocketClient client;
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
            client.readResponse();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void start() {
        try {
            scanner = new Scanner(System.in);
            client = new SocketClient(getPort());
            client.getHandlers().add(new ResponseHandler() {
                @Override
                public boolean canHandle(Object response) {
                    return response instanceof String;
                }

                @Override
                public void handle(Object response, Client client) {
                    System.out.println(response);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getPort() {
        System.out.print("Enter in the local port: ");
        return scanner.nextInt();
    }

    private void stop() {
        try {
            client.close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
