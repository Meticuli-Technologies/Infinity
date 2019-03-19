package com.meti.app;

import com.meti.lib.Client;
import com.meti.lib.OKResponse;
import com.meti.lib.Query;
import com.meti.lib.Update;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/17/2019
 */
public class ClientExecutor {
    private ExecutorService service = Executors.newCachedThreadPool();
    private Query query;
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

    private void buildClient(Socket socket) throws IOException {
        this.client = new Client(socket);
        this.query = new Query(client);

        System.out.println("Connected successfully to " + socket.getInetAddress());

        service.submit(new Updater());
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

    public boolean loop(Scanner scanner) {
        String token = scanner.nextLine();
        if (token.equals("exit")) {
            return false;
        } else {
            try {
                OKResponse response = query.query(new Message(token));
                response.check();
            } catch (Exception e) {
                System.out.println("Failed to write message: " + e.getMessage());
            }
            return true;
        }
    }

    public void start(Scanner scanner) {
        System.out.println("Enter in a username -> ");
        String username = scanner.next();

        try {
            String token = query.query(new Login(username))
                    .getCache(String.class);

            System.out.println(token);
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

    private class Updater implements Callable<Object> {
        @Override
        public Object call() throws Exception {
            while (true) {
                Object token = client.read();
                if (token instanceof Update) {
                    if (token instanceof Message.MessageUpdate) {
                        Message.MessageUpdate update = (Message.MessageUpdate) token;
                        System.out.println("[" + update.user.name + "]: " + update.message.content);
                    }
                } else {
                    System.out.println(token + " not instance of Update");
                    break;
                }
            }
            return null;
        }
    }
}
