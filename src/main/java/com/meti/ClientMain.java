package com.meti;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public class ClientMain {
    public static void main(String[] args) {
        ClientMain clientMain = new ClientMain();
        clientMain.init();
        clientMain.start();
        clientMain.run();
        clientMain.stop();
    }

    private void init() {

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

    }

    private boolean shouldContinue() {
        return false;
    }

    private void loop() {

    }
}
