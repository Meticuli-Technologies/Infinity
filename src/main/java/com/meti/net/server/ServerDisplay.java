package com.meti.net.server;

import com.meti.net.source.SocketSourceSupplier;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ServerDisplay implements Initializable {
    private final SocketSourceSupplier supplier;
    @FXML
    private ListView<String> clientListView;

    @FXML
    private Text portText;

    public ServerDisplay(SocketSourceSupplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        supplier.setOnAccept(socketSource -> Platform.runLater(() -> clientListView.getItems().add(socketSource.getInetAddress().toString())));
        portText.setText(String.valueOf(supplier.getLocalPort()));
    }
}
