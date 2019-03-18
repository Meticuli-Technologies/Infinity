package com.meti.app;

import com.meti.lib.Server;

import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ServerMain {
    private final ExecutorService service = Executors.newCachedThreadPool();
    private Server server;

    private Future<Optional<Exception>> serverFuture;

    public static void main(String[] args) {
        ServerMain main = new ServerMain();
        Scanner scanner = new Scanner(System.in);
        main.init(scanner);
        main.start(scanner);

        while (main.shouldRun(scanner)) {
            main.loop(scanner);
        }

        main.stop();
    }

    private boolean shouldRun(Scanner scanner) {
        String line = scanner.nextLine();
        return !line.equals("exit");
    }

    private void loop(Scanner scanner) {
        //TODO: complete
    }

    private void stop() {
        try {
            server.close();
            service.shutdown();

            if (!service.isTerminated()) {
                Thread.sleep(5000);
                service.shutdownNow();
            }

            if (service.isTerminated()) {
                System.out.println("Stopped server successfully.");
            } else {
                System.out.println("Failed to stop server normally.");
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Failed to stop application: " + e.getMessage());
        }
    }

    private void start(Scanner scanner) {
        this.service.submit(server);
    }

    private void init(Scanner scanner) {
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
}
