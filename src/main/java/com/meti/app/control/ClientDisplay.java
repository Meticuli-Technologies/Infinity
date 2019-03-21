package com.meti.app.control;

import com.meti.app.feature.Message;
import com.meti.app.net.Chat;
import com.meti.lib.State;
import com.meti.lib.net.Request;
import com.meti.lib.respond.OKResponse;
import com.meti.lib.util.TypeFunction;
import com.meti.lib.util.TypePredicate;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class ClientDisplay extends InfinityController implements Initializable {
    @FXML
    private ListView<String> output;

    @FXML
    private TextField input;

    public ClientDisplay(State state) {
        super(state);
    }

    @FXML
    public void handle() {
        try {
            OKResponse response = getClientOrThrow().queryObject(new Message(input.getText()), OKResponse.class);
            assert response != null;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    ArrayList<?> updates = getClientOrThrow().queryObject(new Request("CHAT"), ArrayList.class);
                    updates.stream()
                            .filter(new TypePredicate<>(Chat.ChatUpdate.class))
                            .map(new TypeFunction<>(Chat.ChatUpdate.class))
                            .forEach(ClientDisplay.this::updateChat);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 10000);
    }

    public void updateChat(Chat.ChatUpdate update) {
        if (update.wasAdded) {
            output.getItems().add(update.message.content);
        } else if (update.wasRemoved) {
            output.getItems().remove(update.message.content);
        } else {
            throw new IllegalArgumentException("Invalid update: " + update);
        }
    }
}
