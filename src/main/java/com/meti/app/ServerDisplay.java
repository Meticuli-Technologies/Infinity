package com.meti.app;

import com.meti.lib.Server;
import com.meti.lib.ServiceSubmitter;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/11/2019
 */
public class ServerDisplay {
    private final List<Socket> sockets = new ArrayList<>();
    private final ExecutorService service = Executors.newCachedThreadPool();
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
                    server = new InfinityServer(new ServerSocket(Integer.parseInt(args[1])), new ServiceSubmitter(service));

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

    private class InfinityServer extends Server<InfinityClient, ServiceSubmitter> {
        public InfinityServer(ServerSocket serverSocket, ServiceSubmitter function) {
            super(serverSocket, function, InfinityClient.builder);
        }

        @Override
        public void handleClient(InfinityClient client) {
            log("Located client " + client.socket.getInetAddress());

            clientListView.getItems().add(client.socket.getInetAddress().toString());
        }
    }
}
