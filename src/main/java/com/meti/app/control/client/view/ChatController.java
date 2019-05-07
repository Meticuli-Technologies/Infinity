package com.meti.app.control.client.view;

import com.meti.app.control.client.InfinityClientController;
import com.meti.app.io.update.client.TypeUpdateHandler;
import com.meti.lib.util.collect.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class ChatController extends InfinityClientController implements Initializable {
    @FXML
    private TextField input;

    @FXML
    private TextArea output;

    public ChatController(State state) {
        super(state);
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

        serviceManager.submit((Callable<Void>) () -> {
            while (client.isOpen()) {
                updater.update();
            }
            return null;
        });
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