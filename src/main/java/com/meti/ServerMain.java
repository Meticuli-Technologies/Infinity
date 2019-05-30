package com.meti;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public class ServerMain {
    private Scanner scanner;
    private int port;

    public static void main(String[] args) {
        ServerMain serverMain = new ServerMain();
        serverMain.init();
        serverMain.start();
        serverMain.run();
    }

    private void init() {
        scanner = new Scanner(System.in);
        System.out.print("Enter in a port, or 0 for a local one: ");
        port = scanner.nextInt();
    }

    private ServerSocket serverSocket;

    private void start() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void run() {
        while (shouldContinue()) {
            loop();
        }

        stop();
    }

    private void stop() {
        try {
            serverSocket.close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean shouldContinue() {
        return false;
    }

    private void loop() {

    }
}
