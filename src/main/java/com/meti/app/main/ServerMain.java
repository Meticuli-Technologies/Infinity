package com.meti.app.main;

import com.meti.app.server.InfinityServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/21/2018
 */
public class ServerMain {
    private static final Logger logger = LoggerFactory.getLogger(ServerMain.class);

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
            logger.error("Exception in application:", e);
        }
    }
}
