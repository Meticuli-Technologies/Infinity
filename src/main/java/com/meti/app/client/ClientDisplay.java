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

    private void writeLine(String line) {
        output.appendText(line + '\n');
    }

    private Client client;
    private ResponseProcessor processor;

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