package com.meti.app;

import com.meti.lib.SocketConnection;
import com.meti.lib.fx.Controller;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/1/2019
 */
public class SocketConnectionCreatorView extends Controller implements Initializable {
    @FXML
    TextField addressField;

    @FXML
    TextField portField;

    @FXML
    private Text statusText;

    private final ObjectProperty<Socket> socketProperty = new SimpleObjectProperty<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addressField.setOnKeyPressed(event -> refreshSocket());
        portField.setOnKeyPressed(event -> refreshSocket());
    }

    public void refreshSocket() {
        try {
            socketProperty.set(new Socket(InetAddress.getByName(portField.getText()), Integer.parseInt(statusText.getText())));
            statusText.setText("Nominal");
        } catch (IOException e) {
            socketProperty.set(null);
            statusText.setText(e.getMessage());
        }
    }

    public Optional<SocketConnection> getConnection() throws IOException {
        Socket socket = socketProperty.get();
        if (socket != null) {
            return Optional.of(new SocketConnection(socket));
        } else {
            return Optional.empty();
        }
    }
}
