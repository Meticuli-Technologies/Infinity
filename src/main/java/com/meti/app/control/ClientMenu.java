package com.meti.app.control;

import com.meti.lib.State;
import com.meti.lib.net.Client;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ClientMenu extends InfinityController {
    @FXML
    private TextField addressField;

    @FXML
    private TextField portField;

    public ClientMenu(State state) {
        super(state);
    }

    @FXML
    public void exit() {
        Platform.exit();
    }

    @FXML
    public void next() {
        try {
            Socket socket = new Socket(InetAddress.getByName(addressField.getText()), Integer.parseInt(portField.getText()));
            Client client = new Client(socket);
            state.add(client);

            onto(getClass().getResource("/com/meti/app/control/Login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
