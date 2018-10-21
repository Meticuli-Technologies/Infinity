package com.meti.app.main;

import com.meti.app.server.InfinityServer;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/21/2018
 */
public class ServerMain {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter in a port:");
            int port = Integer.parseInt(scanner.nextLine());

            InfinityServer infinityServer = new InfinityServer(port);
            infinityServer.start();

            boolean running = true;
            while(running){
                running = infinityServer.loop();
            }

            infinityServer.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
