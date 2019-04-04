package com.meti.app.control;

import com.meti.app.core.ServerInfinityController;
import com.meti.lib.collection.State;
import com.meti.lib.net.Client;
import com.meti.lib.net.ServerEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ServerDisplay extends ServerInfinityController implements Initializable {

    @FXML
    private ListView<String> clientList;

    @FXML
    private ListView<String> chatList;

    public ServerDisplay(State state) {
        super(state);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        server.eventManager.compound(ServerEvent.ON_REGISTERED, serverEvent -> ServerDisplay.this.addClient(serverEvent.getClient()));
        server.clients.forEach(this::addClient);
    }

    public void addClient(Client<?> client) {
        clientList.getItems().add(client.getName());
    }
}
