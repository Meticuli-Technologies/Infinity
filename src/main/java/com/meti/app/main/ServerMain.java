package com.meti.app.main;

import com.meti.app.execute.ServerExecutor;

import java.util.Scanner;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/19/2019
 */
class ServerMain {
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
