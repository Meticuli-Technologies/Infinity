package com.meti.app;

import com.meti.app.control.connect.ConnectionCreatorView;
import com.meti.lib.SocketConnection;
import com.meti.lib.fx.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/1/2019
 */
public class SocketConnectionCreator extends Controller implements ConnectionCreatorView.ConnectionCreator<SocketConnection> {

    @FXML
    private TextField addressField;

    @FXML
    private TextField portField;

    @Override
    public String getName() {
        return "Sockets";
    }

    @Override
    public URL getURL() {
        return getClass().getResource("/com/meti/app/SocketConnectionCreator.fxml");
    }

    @Override
    public SocketConnection get() {
        try {
            return new SocketConnection(new Socket(InetAddress.getByName(addressField.getText()), Integer.parseInt(portField.getText())));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
