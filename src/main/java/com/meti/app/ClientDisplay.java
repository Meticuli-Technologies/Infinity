package com.meti.app;

import com.meti.lib.Client;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/11/2019
 */
public class ClientDisplay {
    @FXML
    private TextField input;

    @FXML
    private TextArea output;

    private Client client;

    public Client getClient() {
        if (client == null) {
            throw new IllegalStateException("Client has not been set");
        }

        return client;
    }

    @FXML
    public void handleInput() {
        String text = input.getText();
        input.setText(null);

        if (!text.startsWith("/")) {
            log(text);
        } else {
            String[] args = text.substring(1).split(" ");
            switch (args[0]) {
                case "connect":
                    try {
                        InetAddress address = InetAddress.getByName(args[1]);
                        int port = Integer.parseInt(args[2]);
                        client = new InfinityClient(new Socket(address, port));
                    } catch (IOException e) {
                        log(e);
                    }
                    break;
                case "stop":
                    stop();
                    break;
                case "exit":
                    stop();

                    Platform.exit();
                    break;
                default:
                    log("Unknown command: " + text);
                    break;
            }
        }
    }

    public void stop() {
        try {
            getClient().close();
        } catch (IOException e) {
            log(e);
        }
    }

    public void log(String message) {
        output.appendText(message + "\n");
    }

    public void log(Exception exception) {
        StringWriter writer = new StringWriter();
        exception.printStackTrace(new PrintWriter(writer));
        exception.printStackTrace();
        log(writer.toString());
    }
}
