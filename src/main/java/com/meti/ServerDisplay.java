package com.meti;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/11/2019
 */
public class ServerDisplay {
    private final List<Socket> sockets = new ArrayList<>();
    @FXML
    private ListView<String> clientListView;
    @FXML
    private TextArea chatArea;
    @FXML
    private TextField input;

    @FXML
    public void handleInput() {

    }
}
