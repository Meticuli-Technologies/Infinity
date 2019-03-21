package com.meti.app.execute;

import com.meti.app.feature.Login;
import com.meti.app.feature.Message;
import com.meti.lib.net.Client;
import com.meti.lib.respond.OKResponse;

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
public class ClientExecutor {
    private Client client;

    public boolean init(Scanner scanner) {
        System.out.println("Welcome to Infinity");
        System.out.println("Start by connecting to a server.");

        Optional<Socket> socketOptional = createConnection(scanner);
        if (socketOptional.isPresent()) {
            try {
                buildClient(socketOptional.get());
                return true;
            } catch (IOException e) {
                System.out.println("Failed to build streams: " + e.getMessage());
            }
        } else {
            System.out.println("No connection found.");
        }

        return false;
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

    private void buildClient(Socket socket) throws IOException {
        this.client = new Client(socket);

        System.out.println("Connected successfully to " + socket.getInetAddress());
    }

    public boolean loop(Scanner scanner) {
        String token = scanner.nextLine();
        if (token.equals("exit")) {
            return false;
        } else {
            try {
                OKResponse response = client.queryObject(new Message(""), OKResponse.class);
                System.out.println(response.getCache());
            } catch (Exception e) {
                System.out.println("Failed to write message: " + e.getMessage());
            }
            return true;
        }
    }

    public void start(Scanner scanner) {
        System.out.println("Enter in a username:");
        String username = scanner.nextLine();

        try {
            OKResponse response = client.queryObject(new Login(username), OKResponse.class);
            System.out.println(response.getCache());
        } catch (Exception e) {
            System.out.println("Failed to login: " + e.getMessage());
        }
    }

    public void stop() {
        try {
            client.close();
        } catch (IOException e) {
            System.out.println("Failed to close connection: " + e.getMessage());
        }
    }
}
