package com.meti;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.init();
        main.start();
        main.run();
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

    private boolean shouldContinue() {
        return false;
    }

    private void loop() {

    }

    private void stop() {

    }
}