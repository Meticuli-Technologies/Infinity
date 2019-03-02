package com.meti.app;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.net.ServerSocket;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/2/2019
 */
public class ServerDisplay {
    @FXML
    private ListView<String> clientListView;

    public void load(ServerSocket serverSocket) {

    }
}
