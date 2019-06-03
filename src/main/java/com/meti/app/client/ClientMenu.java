package com.meti.app.client;

import com.meti.app.Controls;
import com.meti.lib.fx.InjectorLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.List;

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

    private final Controls controls;

    public ClientMenu(Controls controls) {
        this.controls = controls;
    }

    @FXML
    public void connect(){
        try {
            InetAddress address = InetAddress.getByName(addressField.getText());
            int port = Integer.parseInt(portField.getText());

            ClientBootstrap bootstrap = new SocketClientBootstrap(address, port);
            controls.getState().add(bootstrap);

            Parent root = InjectorLoader.load(List.of(controls), getClientDisplayURL());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private URL getClientDisplayURL() {
        return getClass().getResource("/com/meti/app/client/ClientDisplay");
    }
}
