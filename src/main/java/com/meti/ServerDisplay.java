package com.meti;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
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
                service.submit(() -> {
                    while(!serverSocket.isClosed()){
                        try {
                            Socket socket = serverSocket.accept();
                            service.submit(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                                        while (!socket.isClosed()) {
                                            String line;
                                            while ((line = reader.readLine()) != null) {
                                                processLine(line);
                                            }
                                        }

                                    } catch (IOException e) {
                                        logMessage("Failed to read " + socket.toString(), e);
                                    }
                                }
                            });
                        } catch (IOException e) {
                            logMessage("Failed to accept socket", e);
                        }
                    }
                });
            } catch (IOException e) {
                logMessage("Failed to start server on " + port, e);
            }
        }
    }

    private void processLine(String line) {

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
        chatArea.appendText("[" + user + "]: " + name);
    }
}
