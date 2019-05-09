package com.meti;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ServerDisplay {
    @FXML
    private ListView<String> clientListView;

    private final Server server;

    public ServerDisplay(Server server) {
        this.server = server;
    }
}
