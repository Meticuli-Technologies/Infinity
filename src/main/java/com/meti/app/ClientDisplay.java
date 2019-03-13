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
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/11/2019
 */
public class ClientDisplay {
    private final ExecutorService service = Executors.newCachedThreadPool();
    @FXML
    private TextField input;
    @FXML
    private TextArea output;
    private Client client;

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

                        client = service.submit(() -> new InfinityClient(new Socket(address, port))).get(1000, TimeUnit.MILLISECONDS);

                        log("Connected to server successfully");
                    } catch (Exception e) {
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

            service.shutdown();
            if (!service.isTerminated()) {
                List<Runnable> runnables = service.shutdownNow();
                log("Stopped client with " + runnables.size() + " tasks still running");
            }
        } catch (IOException e) {
            log(e);
        }
    }

    public Client getClient() {
        if (client == null) {
            throw new IllegalStateException("Client has not been set");
        }

        return client;
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
