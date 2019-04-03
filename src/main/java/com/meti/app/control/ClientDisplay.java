package com.meti.app.control;

import com.meti.app.core.ClientInfinityController;
import com.meti.lib.collection.State;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

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
            Object o = querier.query(new Message(client.getName(), input.getText())).get();
            if(o instanceof Exception){
                throw (Exception) o;
            }
            input.clear();
        } catch (Exception e) {
            console.log(Level.WARNING, e);
        }
    }
}
