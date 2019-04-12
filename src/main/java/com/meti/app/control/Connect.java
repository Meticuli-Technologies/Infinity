package com.meti.app.control;

import com.meti.lib.net.Client;
import com.meti.lib.net.Querier;
import com.meti.lib.util.State;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class Connect extends InfinityController {
    @FXML
    private TextField addressField;

    @FXML
    private TextField portField;

    public Connect(State state) {
        super(state);
    }

    @FXML
    public void back() {
        try {
            onto(getClass().getResource("/com/meti/Menu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void next() {
        try {
            Socket socket = new Socket(InetAddress.getByName(addressField.getText()), Integer.parseInt(portField.getText()));
            Client client = new Client(socket);
            state.add(client);

            Querier querier = new Querier(client);
            state.add(querier);
            service.submit(querier);

            onto(getClass().getResource("/com/meti/Login.fxml"));
        } catch (IOException e) {
            console.log(Level.WARNING, e);
        }
    }
}
