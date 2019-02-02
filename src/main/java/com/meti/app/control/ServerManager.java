package com.meti.app.control;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/2/2019
 */
public class ServerManager extends InfinityController {
    @FXML
    private AnchorPane dataPane;

    @FXML
    private ListView<String> serverListView;

    @FXML
    public void addServer() {

    }

    @FXML
    public void closeServer() {

    }

    @FXML
    public void clearServers() {

    }

    @FXML
    public void doContinue() {

    }

    @FXML
    public void back() {
        toBack();
    }
}
