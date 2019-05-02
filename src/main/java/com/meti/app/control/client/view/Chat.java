package com.meti.app.control.client.view;

import com.meti.app.control.client.InfinityClientController;
import com.meti.lib.util.collect.State;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Chat extends InfinityClientController {
    @FXML
    private TextField input;

    @FXML
    private TextArea output;

    public Chat(State state) {
        super(state);
    }

    @FXML
    public void handleInput(){

    }
}