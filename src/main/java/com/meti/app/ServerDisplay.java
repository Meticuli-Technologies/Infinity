package com.meti.app;

import com.meti.lib.Server;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/11/2019
 */
public class ServerDisplay {
    private final List<Socket> sockets = new ArrayList<>();

    private Server server;

    @FXML
    private ListView<String> clientListView;

    @FXML
    private TextArea chatArea;
    
    @FXML
    private TextField input;

    @FXML
    public void handleInput() {
        String text = input.getText();
        if (!text.startsWith("/")) {
            log(text.substring(1));
        }

        String[] args = text.split(" ");
        switch (args[0]) {
            case "start":
                try {
                    server = new InfinityServer(new ServerSocket(Integer.parseInt(args[1])));

                    log("Successfully started server on " + server.serverSocket.getLocalPort());
                    log("Listening for clients at " + server.serverSocket.getInetAddress());
                } catch (IOException e) {
                    log(e);
                }
                break;
            default:
                log("Unknown command: " + text);
                break;
        }
    }

    public void log(String message) {
        chatArea.appendText(message + "\n");
    }

    public void log(Exception exception) {
        StringWriter writer = new StringWriter();
        exception.printStackTrace(new PrintWriter(writer));
        log(writer.toString());
    }
}
