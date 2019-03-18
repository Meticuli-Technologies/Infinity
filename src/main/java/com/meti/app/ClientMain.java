package com.meti.app;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.Scanner;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/17/2019
 */
public class ClientMain {
    private Socket socket;

    public static void main(String[] args) {
        ClientMain main = new ClientMain();
        main.init();
        main.start();
    }

    private void init() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Infinity");
        System.out.println("Start by connecting to a server.");

        Optional<Socket> socketOptional = createConnection(scanner);
        if (socketOptional.isPresent()) {
            System.out.println("Connected successfully.");

            this.socket = socketOptional.get();
        } else {
            System.out.println("No connection found.");
        }
    }

    private Optional<Socket> createConnection(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Press ENTER to continue, or enter any key to exit.");
                String line = scanner.nextLine();
                if (line.length() == 0) {
                    return Optional.of(getSocket(scanner));
                } else {
                    return Optional.empty();
                }
            } catch (IOException e) {
                System.out.println("Invalid connection: " + e.getMessage() + ", please try again!");
            }
        }
    }

    private Socket getSocket(Scanner scanner) throws IOException {
        InetAddress address = getAddress(scanner);
        int port = getPort(scanner);
        return new Socket(address, port);
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

    public Socket getSocket(){
        if(socket == null){
            throw new IllegalStateException("Socket has not been set!");
        }

        return socket;
    }

    private void start() {
        Socket socket = getSocket();

    }
}
