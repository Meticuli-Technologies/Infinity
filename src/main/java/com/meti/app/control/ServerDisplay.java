package com.meti.app.control;

import com.meti.app.core.InfinityController;
import com.meti.app.core.ServerInfinityController;
import com.meti.lib.collection.State;
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

    }
}
