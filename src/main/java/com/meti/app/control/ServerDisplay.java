package com.meti.app.control;

import com.meti.lib.State;
import com.meti.lib.net.Server;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ServerDisplay extends InfinityController implements Initializable {
    @FXML
    private ListView<String> clientsView;

    public ServerDisplay(State state) {
        super(state);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Server server = getServer();
        server.onConnect = server.onConnect.andThen(client -> clientsView.getItems().add(client.socket.getInetAddress().toString()));
    }
}
