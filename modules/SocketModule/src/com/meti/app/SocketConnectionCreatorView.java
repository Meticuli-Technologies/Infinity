package com.meti.app;

import com.meti.app.control.InfinityController;
import com.meti.lib.SocketConnection;
import com.meti.lib.concurrent.ThreadManager;
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
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/1/2019
 */
public class SocketConnectionCreatorView extends InfinityController implements Initializable {
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
                if (now % 100 == 0) {
                    refresh();
                }
            }
        };
        timer.start();
    }

    public void refresh() {
        try {
            InetAddress address = InetAddress.getByName(addressField.getText());
            int port = Integer.parseInt(portField.getText());

            Future<String> result = state.get().singleContent(ThreadManager.class)
                    .submit(() -> socketListener(address, port));

            statusText.setText(result.get(10, TimeUnit.MILLISECONDS));
        } catch (Exception e) {
            console.log(Level.WARNING, e);
        }
    }

    public String socketListener(InetAddress address, int port) {
        try {
            socketProperty.set(new Socket(address, port));
            return "Nominal";
        } catch (Exception e) {
            socketProperty.set(null);
            return e.getMessage();
        }
    }

    public Optional<SocketConnection> getConnection() throws IOException {
        refresh();

        Socket socket = socketProperty.get();
        if (socket != null) {
            return Optional.of(new SocketConnection(socket));
        } else {
            return Optional.empty();
        }
    }
}
