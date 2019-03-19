package com.meti.app;

import java.util.Scanner;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/19/2019
 */
public class ClientMain {
    public static void main(String[] args) {
        ClientExecutor main = new ClientExecutor();
        Scanner scanner = new Scanner(System.in);
        boolean shouldStart = main.init(scanner);

        if (shouldStart) {
            main.start(scanner);

            while (true) {
                if (!main.loop(scanner)) break;
            }
        }

        main.stop();
    }
}
