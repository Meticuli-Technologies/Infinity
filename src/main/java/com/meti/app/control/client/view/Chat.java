package com.meti.app.control.client.view;

import com.meti.app.control.client.InfinityClientController;
import com.meti.app.io.update.Update;
import com.meti.app.io.update.client.TypeUpdateHandler;
import com.meti.lib.util.collect.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class Chat extends InfinityClientController implements Initializable {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updater.handlers.add(new TypeUpdateHandler<>(ChatUpdate.class) {
            @Override
            public void handle(ChatUpdate update) {
                output.appendText(update.value);
                output.appendText("\n");
            }
        });
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