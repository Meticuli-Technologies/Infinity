package com.meti;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ServerSocket;

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
            } catch (IOException e) {
                logMessage("Failed to start server on " + port, e);
            }
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
        chatArea.appendText("[" + user + "]: " + name);
    }
}
