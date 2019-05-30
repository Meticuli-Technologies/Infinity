package com.meti;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.function.Supplier;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public class ClientMain {
    private final Client client = new Client();
    private Scanner scanner;

    public static void main(String[] args) {
        ClientMain clientMain = new ClientMain();
        clientMain.start();
        clientMain.run();
    }

    private boolean isNotExitMessage(String message) {
        return !message.equals("exit");
    }

    private void loop(Supplier<String> supplier) {
        if (isNotExitMessage(supplier.get())) {
            writeMessage(supplier.get());
            loop(supplier);
        }
    }

    private void run() {
        loop(() -> scanner.nextLine());
        stop();
    }

    private void writeMessage(String message) {
        try {
            writeToStream(message, client.getOutputStream());
            processResponse(client.getInputStream().readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void writeToStream(String message, ObjectOutput outputStream) throws IOException {
        outputStream.writeObject(message);
        outputStream.flush();
    }

    private void processResponse(Object response) {
        if (response instanceof Throwable) {
            ((Throwable) response).printStackTrace();
        } else {
            processResponseNonThrowable(response);
        }
    }

    private void processResponseNonThrowable(Object response) {
        if (response instanceof String) {
            System.out.println(response);
        } else {
            throw new UnsupportedOperationException("Unknown response: " + response);
        }
    }

    private void start() {
        try {
            scanner = new Scanner(System.in);
            int port = getPort();
            bindToSocket(new Socket(InetAddress.getLocalHost(), port));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getPort() {
        System.out.print("Enter in the local port: ");
        return scanner.nextInt();
    }

    private void bindToSocket(Socket socket) throws IOException {
        client.setSocket(socket);
            /*
            The OOS must be constructed before the OIS because of the header.
             */
        client.setOutputStream(new ObjectOutputStream(client.getSocket().getOutputStream()));
        client.setInputStream(new ObjectInputStream(client.getSocket().getInputStream()));
    }

    private void stop() {
        try {
            client.getSocket().close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
