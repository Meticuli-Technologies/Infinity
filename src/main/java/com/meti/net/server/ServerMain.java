package com.meti.net.server;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public class ServerMain {
    private final Scanner scanner = new Scanner(System.in);
    private Server server;
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
            server = new InfinityServer(port);
            server.listen();

            System.out.println("Started server on port: " + server.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stop() {
        try {
            scanner.close();
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
