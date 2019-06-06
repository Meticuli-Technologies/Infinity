package com.meti.app.client;

import com.meti.app.Controls;
import com.meti.lib.net.client.Client;
import com.meti.lib.net.client.SocketClient;
import com.meti.lib.net.client.handle.ClientProcessor;
import com.meti.lib.net.client.handle.ResponseHandler;
import com.meti.lib.net.client.handle.ResponseProcessor;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/1/2019
 */
public class ClientDisplay extends InfinityController implements Initializable {
    @FXML
    private TextArea output;

    @FXML
    private TextField input;

    public ClientDisplay(Controls controls) {
        super(controls);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Optional<ClientBootstrap> clientBootstrap = state.singleByClass(ClientBootstrap.class);
        if (clientBootstrap.isPresent()) {
            loadClient(clientBootstrap.get());
        } else {
            writeLine("No bootstrap was found.");
        }
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
            tryLoadClient(clientBootstrap);
        } catch (IOException e) {
            writeLine(e.getLocalizedMessage());
        }
    }

    private void tryLoadClient(ClientBootstrap clientBootstrap) throws IOException {
        client = new SocketClient(clientBootstrap);
        processor = new ClientProcessor(client);
        processor.addHandler(new OutputHandler());
        state.add(client);
    }

    private class OutputHandler extends StringTypeHandler {
        @Override
        public Optional<Serializable> handle(Object response, Client client) {
            writeLine(response.toString());
            return Optional.empty();
        }
    }
}