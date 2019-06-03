package com.meti.app.client;

import com.meti.lib.collect.State;
import com.meti.lib.net.client.Client;
import com.meti.lib.net.client.SocketClient;
import com.meti.lib.net.client.handle.ClientProcessor;
import com.meti.lib.net.client.handle.ResponseHandler;
import com.meti.lib.net.client.handle.ResponseProcessor;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/1/2019
 */
public class ClientDisplay {
    @FXML
    private TextArea output;

    @FXML
    private TextField input;

    @FXML
    private Text statusText;

    @FXML
    private TextField portField;

    @FXML
    private Button changePortButton;

    private boolean shouldChangePort = true;

    private final State state;

    public ClientDisplay(State state) {
        this.state = state;
    }

    @FXML
    public void nextInput() {
        try {
            String message = input.getText();
            input.clear();
            client.writeAndFlush(message);
            processor.processNextResponse();
        } catch (Throwable throwable) {
            writeLine(throwable.getLocalizedMessage());
        }
    }

    @FXML
    public void changePort(){
        if (shouldChangePort) {
            if (client != null) {
                disconnectFromPort(client);
            }

            portField.setEditable(true);
            changePortButton.setText("Reconnect");
        } else {
            connectToPort();

            portField.setEditable(false);
            changePortButton.setText("Change Port");
        }
        shouldChangePort = !shouldChangePort;
    }

    private void writeLine(String line) {
        output.appendText(line + '\n');
    }

    private void connectToPort() {
        String portValue = portField.getText();
        try {
            int port = Integer.parseInt(portValue);
            loadClient(new SocketClientBootstrap(port));
            statusText.setText("Successfully connected to server.");
        } catch (NumberFormatException | UnknownHostException e) {
            statusText.setText("Invalid integer: " + portValue);
        }
    }

    private Client client;
    private ResponseProcessor processor;

    private void disconnectFromPort(Closeable client) {
        try {
            client.close();
        } catch (IOException e) {
            writeLine(e.getLocalizedMessage());
        }
    }

    private void loadClient(ClientBootstrap clientBootstrap) {
        try {
            client = new SocketClient(clientBootstrap);
            processor = new ClientProcessor(client);
            processor.addHandler(new OutputHandler());

            state.add(client);
        } catch (IOException e) {
            statusText.setText(e.getLocalizedMessage());
        }
    }

    private class OutputHandler implements ResponseHandler {
        @Override
        public boolean canHandle(Object response) {
            return response instanceof String;
        }

        @Override
        public Optional<Serializable> handle(Object response, Client client) {
            writeLine(response.toString());
            return Optional.empty();
        }
    }
}