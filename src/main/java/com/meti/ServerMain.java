package com.meti;

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

    private void start() {

    }

    private void run() {
        while (shouldContinue()) {
            loop();
        }

        stop();
    }

    private void stop() {
        scanner.close();
    }

    private boolean shouldContinue() {
        return false;
    }

    private void loop() {

    }
}
