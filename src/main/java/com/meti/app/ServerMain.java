package com.meti.app;

import com.meti.lib.Server;

import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class ServerMain {
    private Server server;

    private Future<Optional<Exception>> serverFuture;
    private FutureTask<Void> serverTask;

    public static void main(String[] args) {
        ServerMain main = new ServerMain();
        Scanner scanner = new Scanner(System.in);
        main.init(scanner);
        main.start(scanner);
    }

    private void start(Scanner scanner) {

    }

    private void init(Scanner scanner) {
        try {
            int port = getPort(scanner);
            this.server = new InfinityServer(port);
            this.serverTask = new FutureTask<>(server);
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
