package com.meti;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/11/2019
 */
public class ServerDisplay {
    @FXML
    private ListView<String> clientListView;

    @FXML
    private TextArea chatArea;

    @FXML
    private TextField input;

    private final List<Socket> sockets = new ArrayList<>();

    @FXML
    public void handleInput() {
        String command = input.getText();
        if (!command.startsWith("/")) {
            logMessage(command);
        }

        String withoutSlash = command.replace('/', ' ');
        String[] arguments = withoutSlash.split(" ");
        if (arguments[0].equals("start")) {
            int port = Integer.parseInt(arguments[1]);
            logMessage("Starting server on " + port);

            try {
                ServerSocket serverSocket = new ServerSocket(port);

                ExecutorService service = Executors.newCachedThreadPool();
                service.submit(new ServerHandler(serverSocket, service));
            } catch (IOException e) {
                logMessage("Failed to start server on " + port, e);
            }
        }
    }

    private void processLine(String line) {
        String[] args = line.split(" ");
        switch (args[0]) {
            case "log":
                logMessage(String.join(" ", Arrays.copyOfRange(args, 1, args.length)));
                break;
            default:
                throw new IllegalArgumentException("Cannot process " + line);
        }
    }

    public void logMessage(String name) {
        logMessage("server", name);
    }

    public void logMessage(String message, Exception exception) {
        logMessage(message);
        logMessage(exception);
    }

    public void logMessage(Exception exception) {
        StringWriter writer = new StringWriter();
        exception.printStackTrace(new PrintWriter(writer));
        logMessage(writer.toString());
    }

    public void logMessage(String user, String name){
        String text = "[" + user + "]: " + name;
        chatArea.appendText(text);
    }

    private class ServerHandler implements Runnable {
        private final ServerSocket serverSocket;
        private final ExecutorService service;

        public ServerHandler(ServerSocket serverSocket, ExecutorService service) {
            this.serverSocket = serverSocket;
            this.service = service;
        }

        @Override
        public void run() {
            while (!serverSocket.isClosed()) {
                try {
                    Socket socket = serverSocket.accept();
                    sockets.add(socket);

                    ServerDisplay.this.logMessage("Located user " + socket.getInetAddress());
                    service.submit(() -> {
                        try {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                            while (!socket.isClosed()) {
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    try {
                                        ServerDisplay.this.processLine(line);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                        } catch (IOException e) {
                            ServerDisplay.this.logMessage("Failed to read " + socket.toString(), e);
                        }
                    });
                } catch (IOException e) {
                    ServerDisplay.this.logMessage("Failed to accept socket", e);
                }
            }
        }
    }
}
