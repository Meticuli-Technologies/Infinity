package com.meti.app;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/17/2019
 */
public class ClientMain {
    public static void main(String[] args) {
        ClientMain main = new ClientMain();
        main.init();
        main.start();
    }

    private void init() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Infinity");
        System.out.println("Start by connecting to a server.");

        Socket socket = getSocket(scanner);

        System.out.println(socket);
    }

    private Socket getSocket(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Please enter in the following properties:");
                InetAddress address = getAddress(scanner);
                int port = getPort(scanner);
                return new Socket(address, port);
            } catch (IOException e) {
                System.out.println("Invalid connection: " + e.getMessage() + ", please try again!");
            }
        }
    }

    private InetAddress getAddress(Scanner scanner) {
        while (true) {
            System.out.print("Enter in the address: ");
            String addressToken = scanner.next();
            try {
                return InetAddress.getByName(addressToken);
            } catch (UnknownHostException e) {
                System.out.println("Invalid address: " + addressToken);
            }
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

    private void start() {

    }
}
