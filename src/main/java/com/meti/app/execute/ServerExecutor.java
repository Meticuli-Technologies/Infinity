package com.meti.app.execute;

import com.meti.app.net.InfinityServer;
import com.meti.lib.net.Server;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerExecutor {
    private final ExecutorService service = Executors.newCachedThreadPool();
    private Server server;

    public void init(Scanner scanner) {
        System.out.println("Welcome to Infinity");
        System.out.println("Start by hosting server.");

        try {
            int port = getPort(scanner);
            this.server = new InfinityServer(port);

            System.out.println("Initialized server on port " + port);
        } catch (IOException e) {
            System.out.println("Failed to initialize. " + e.getMessage());
        }
    }

    private int getPort(Scanner scanner) {
        while (true) {
            System.out.print("Enter in the port: ");
            String portToken = scanner.next();
            try {
                return Integer.parseInt(portToken);
            } catch (NumberFormatException e) {
                System.out.println("Invalid port: " + portToken);
            }
        }
    }

    public boolean shouldRun(Scanner scanner) {
        String line = scanner.nextLine();
        boolean result = !line.equals("exit");
        if (!result) {
            System.out.println("Exiting server.");
            return false;
        } else {
            return true;
        }
    }

    public void start() {
        this.service.submit(server);
    }

    public void stop() {
        System.out.println("Stopping server...");

        try {
            server.close();
            service.shutdown();

            if (!service.isTerminated()) {
                Thread.sleep(5000);
                service.shutdownNow();
            }

            if (service.isTerminated()) {
                System.out.println("Stopped server successfully.");
                System.exit(0);
            } else {
                System.out.println("Failed to stop server normally.");
                System.exit(-1);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Failed to stop application: " + e.getMessage());
        }
    }
}
