package com.meti;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ServerDisplay implements Initializable {
    private final SocketSourceSupplier supplier;
    @FXML
    private ListView<String> clientListView;

    public ServerDisplay(SocketSourceSupplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        supplier.setOnAccept(socketSource -> clientListView.getItems().add(socketSource.getInetAddress().toString()));
    }
}
