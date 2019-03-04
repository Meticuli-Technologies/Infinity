package com.meti.app;

import com.meti.lib.State;
import com.meti.lib.fx.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.InetAddress;
import java.net.Socket;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/24/2019
 */
public class ClientMenu extends Controller {
    @FXML
    private TextField address;

    @FXML
    private TextField port;

    public ClientMenu(State state) {
        super(state);
    }

    @FXML
    public void back() {
        try {
            ontoURL(getClass().getResource("/com/meti/app/Menu.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void next() {
        try {
            Socket socket = new Socket(InetAddress.getByName(address.getText()), Integer.parseInt(port.getText()));
            ClientDisplay menu = ontoURL(getClass().getResource("/com/meti/app/ClientDisplay.fxml"));
            menu.load(socket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
