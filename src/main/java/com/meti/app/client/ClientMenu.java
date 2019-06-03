package com.meti.app.client;

import com.meti.lib.collect.State;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/3/2019
 */
public class ClientMenu {
    @FXML
    private TextField addressField;

    @FXML
    private TextField portField;

    private final State state;

    public ClientMenu(State state) {
        this.state = state;
    }

    @FXML
    public void connect(){
        try {
            InetAddress address = InetAddress.getByName(addressField.getText());
            int port = Integer.parseInt(portField.getText());

            ClientBootstrap bootstrap = new SocketClientBootstrap(address, port);
            state.add(bootstrap);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
