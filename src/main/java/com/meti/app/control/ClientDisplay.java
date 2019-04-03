package com.meti.app.control;

import com.meti.app.core.ClientInfinityController;
import com.meti.lib.collection.State;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ClientDisplay extends ClientInfinityController {

    @FXML
    private TextField input;

    @FXML
    private ListView<String> output;

    public ClientDisplay(State state) {
        super(state);
    }

    @FXML
    public void handle(){

    }
}
