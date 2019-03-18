package com.meti.app;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    private Client client;

    public static void main(String[] args) {
        ClientMain main = new ClientMain();
        Scanner scanner = new Scanner(System.in);
        main.init(scanner);
        main.start(scanner);
    }

    private void init(Scanner scanner) {
        System.out.println("Welcome to Infinity");
        System.out.println("Start by connecting to a server.");

        Optional<Socket> socketOptional = createConnection(scanner);
        if (socketOptional.isPresent()) {
            try {
                Socket socket = socketOptional.get();
                ObjectOutputStream outputStream = new ObjectOutputStream(client.getSocket().getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(client.getSocket().getInputStream());

                this.client = new Client(socket, inputStream, outputStream);

                System.out.println("Connected successfully to " + socket.getInetAddress());
            } catch (IOException e) {
                System.out.println("Failed to build streams: " + e.getMessage());
            }
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

    private void start(Scanner scanner) {
        System.out.println("Enter in a username and password.");

        System.out.print("Username -> ");
        String username = scanner.nextLine();

        System.out.print("Password -> ");
        String password = scanner.nextLine();

        try {
            client.write(new Login(username, password));
        } catch (IOException e) {
            System.out.println("Failed to login: " + e.getMessage());
        }

        try {
            client.close();
        } catch (IOException e) {
            System.out.println("Failed to close connection: " + e.getMessage());
        }
    }
}
