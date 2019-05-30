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
    private Scanner scanner;
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

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
            writeToStream(message, outputStream);
            processResponse(inputStream.readObject());
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
            bindToPort(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getPort() {
        System.out.print("Enter in the local port: ");
        return scanner.nextInt();
    }

    private void bindToPort(int port) throws IOException {
        socket = new Socket(InetAddress.getLocalHost(), port);
            /*
            The OOS must be constructed before the OIS because of the header.
             */
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
    }

    private void stop() {
        try {
            socket.close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
