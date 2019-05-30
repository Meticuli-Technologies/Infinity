package com.meti.lib.net.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public class ServerMain {
    private final Scanner scanner = new Scanner(System.in);
    private ClientAcceptor acceptor;
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

    private void start() {
        try {
            ServerSocket serverSocket = buildServerSocket(port);
            acceptor = new InfinityClientAcceptor(serverSocket);
            acceptor.listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ServerSocket buildServerSocket(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Launched server on port " + serverSocket.getLocalPort() + '.');
        return serverSocket;
    }

    private void stop() {
        try {
            scanner.close();
            acceptor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
