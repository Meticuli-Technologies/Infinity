package com.meti;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

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
        clientMain.init();
        clientMain.start();
        clientMain.run();
        clientMain.stop();
    }

    private void init() {
        scanner = new Scanner(System.in);
    }

    private void start() {
        try {
            System.out.print("Enter in the local port: ");
            int port = scanner.nextInt();

            socket = new Socket(InetAddress.getLocalHost(), port);

            /*
            The OOS must be constructed before the OIS because of the header.
             */
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void run() {
        while (shouldContinue()) {
            loop();
        }

        stop();
    }

    private void stop() {
        try {
            socket.close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean shouldContinue = true;

    private void loop() {
        String message = scanner.nextLine();
        if (message.equals("exit")) {
            shouldContinue = false;
        } else {
            try {
                outputStream.writeObject(message);
                outputStream.flush();

                Object response = inputStream.readObject();
                if (response instanceof Throwable) {
                    ((Throwable) response).printStackTrace();
                } else {
                    if (response instanceof String) {
                        System.out.println(response);
                    } else {
                        throw new UnsupportedOperationException("Unknown response: " + response);
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean shouldContinue() {
        return shouldContinue;
    }
}
