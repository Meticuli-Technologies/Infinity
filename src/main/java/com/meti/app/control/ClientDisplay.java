package com.meti.app.control;

import com.meti.app.feature.Message;
import com.meti.app.net.Chat;
import com.meti.lib.State;
import com.meti.lib.net.Request;
import com.meti.lib.respond.OKResponse;
import com.meti.lib.util.TypeFunction;
import com.meti.lib.util.TypePredicate;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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

            input.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            try {
                List<?> updateTokens = getClientOrThrow().queryObject(new Request("CHAT"), List.class);
                if (!updateTokens.isEmpty()) {
                    Set<Chat.ChatUpdate> updates = updateTokens.stream()
                            .filter(new TypePredicate<>(Chat.ChatUpdate.class))
                            .map(new TypeFunction<>(Chat.ChatUpdate.class))
                            .collect(Collectors.toSet());

                    Platform.runLater(() -> updateChat(updates));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void updateChat(Set<Chat.ChatUpdate> updates) {
        for (Chat.ChatUpdate update : updates) {
            if (update.wasAdded) {
                output.getItems().add(update.message.content);
            } else if (update.wasRemoved) {
                output.getItems().remove(update.message.content);
            } else {
                throw new IllegalArgumentException("Invalid update: " + update);
            }
        }
    }
}
