package com.meti.app;

import com.meti.lib.SocketConnection;
import com.meti.lib.fx.Controller;
import com.meti.lib.util.Counter;
import javafx.animation.AnimationTimer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
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
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now % 512 == 0) {
                    refreshSocket();
                }
            }
        };
        timer.start();
    }

    public void refreshSocket() {
        try {
            socketProperty.set(new Socket(InetAddress.getByName(addressField.getText()), Integer.parseInt(portField.getText())));
            statusText.setText("Nominal");
        } catch (Exception e) {
            socketProperty.set(null);
            statusText.setText(e.getMessage());
        }
    }

    public Optional<SocketConnection> getConnection() throws IOException {
        refreshSocket();

        Socket socket = socketProperty.get();
        if (socket != null) {
            return Optional.of(new SocketConnection(socket));
        } else {
            return Optional.empty();
        }
    }
}
