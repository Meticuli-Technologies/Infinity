package com.meti.app.control;

import com.meti.app.core.ClientInfinityController;
import com.meti.lib.collection.State;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.logging.Level;

public class ClientDisplay extends ClientInfinityController {

    @FXML
    private TextField input;

    @FXML
    private ListView<String> output;

    public ClientDisplay(State state) {
        super(state);
    }

    @FXML
    public void handle() {
        try {
            querier.query(new Message(client.getName(), input.getText()));
            input.clear();
        } catch (IOException e) {
            console.log(Level.WARNING, e);
        }
    }
}
