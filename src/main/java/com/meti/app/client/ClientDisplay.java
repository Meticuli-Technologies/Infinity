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

import java.io.IOException;
import java.io.Serializable;
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
            client.writeAndFlush(message);
            processor.processNextResponse();
        } catch (Throwable throwable) {
            writeLine(throwable.getLocalizedMessage());
        }
    }

    @FXML
    public void changePort(){
        if (shouldChangePort) {
            disconnectToPort();

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
            loadClient(port);
        } catch (NumberFormatException e) {
            statusText.setText("Invalid integer: " + portValue);
        }
    }

    private Client client;
    private ResponseProcessor processor;

    private void disconnectToPort() {
        try {
            client.close();
        } catch (IOException e) {
            writeLine(e.getLocalizedMessage());
        }
    }

    private void loadClient(int port) {
        try {
            client = new SocketClient(port);
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