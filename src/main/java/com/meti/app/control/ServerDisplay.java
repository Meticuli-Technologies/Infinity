package com.meti.app.control;

import com.meti.app.core.InfinityController;
import com.meti.lib.collection.State;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ServerDisplay extends InfinityController {

    @FXML
    private ListView<String> clientList;

    @FXML
    private ListView<String> chatList;

    public ServerDisplay(State state) {
        super(state);
    }
}
