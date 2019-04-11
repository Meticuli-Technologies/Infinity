package com.meti.app;

import com.meti.lib.util.State;
import com.meti.app.control.InfinityServerController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class ServerDisplay extends InfinityServerController implements Initializable {
    @FXML
    private ListView<InetAddress> clientListView;

    @FXML
    private ListView<String> chatListView;

    public ServerDisplay(State state) {
        super(state);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        server.onAccept = client -> clientListView.getItems().add(client.socket.getInetAddress());
    }
}
