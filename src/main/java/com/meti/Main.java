package com.meti;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public class Main {
    private final Logger logger = Logger.getLogger("Infinity");
    private final Scanner scanner = new Scanner(System.in);
    private ServerSocket serverSocket;

    public static void main(String[] args) {
        Main main = new Main();
        main.init();
        main.start();
        main.run();
    }

    private void init() {
        try {
            this.serverSocket = new ServerSocket(0);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to initialize Infinity", e);
        }
    }

    private void start() {

    }

    private void run() {
        while (shouldContinue()) {
            loop();
        }

        stop();
    }

    private boolean shouldContinue() {
        return false;
    }

    private void loop() {

    }

    private void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to stop Infinity", e);
        }
    }
}