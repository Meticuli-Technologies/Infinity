package com.meti.app.client;

import com.meti.app.StringResponseHandler;
import com.meti.lib.net.client.Client;
import com.meti.lib.net.client.SocketClient;
import com.meti.lib.net.client.handle.ClientProcessor;
import com.meti.lib.net.client.handle.ResponseProcessor;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

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

    private void disconnectToPort() {

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

    private void loadClient(int port) {
        try {
            client = new SocketClient(port);
            processor = new ClientProcessor(client);
            processor.addHandler(new StringResponseHandler());
        } catch (IOException e) {
            statusText.setText(e.getLocalizedMessage());
        }
    }
}