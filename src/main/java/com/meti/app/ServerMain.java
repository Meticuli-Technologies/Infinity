package com.meti.app;

import com.meti.lib.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerMain {
    private Server server;

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
            this.server = new Server(new ServerSocket(port)) {
                @Override
                public void handleAccept(Socket accept) throws Exception {

                }
            };
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
