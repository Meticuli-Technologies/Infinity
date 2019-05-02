package com.meti.app.control.client.view;

import com.meti.app.control.client.InfinityClientController;
import com.meti.lib.util.collect.State;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class Chat extends InfinityClientController {
    @FXML
    private TextField input;

    @FXML
    private TextArea output;

    public Chat(State state) {
        super(state);
    }

    @FXML
    public void handleInput(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            tryWriteMessage();
        }
    }

    private void tryWriteMessage() {
        try {
            writeMessage(input).clear();
        } catch (Exception e) {
            console.log(Level.WARNING, e);
        }
    }

    private TextField writeMessage(TextField input) throws Exception {
        String value = input.getText();
        querier.query(new ChatMessage(value)).get(1, TimeUnit.SECONDS);
        return input;
    }
}