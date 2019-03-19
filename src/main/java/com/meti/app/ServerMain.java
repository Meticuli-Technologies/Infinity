package com.meti.app;

import java.util.Scanner;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/19/2019
 */
public class ServerMain {
    public static void main(String[] args) {
        ServerExecutor main = new ServerExecutor();
        Scanner scanner = new Scanner(System.in);
        main.init(scanner);
        main.start();

        while (main.shouldRun(scanner)) {
            main.loop();
        }

        main.stop();
    }
}
