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

import java.net.SocketException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ExecutionException;
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
            OKResponse response = getClient().queryObject(new Message(input.getText(), getUser()), OKResponse.class);
            assert response != null;

            input.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        try {
            executor.scheduleAtFixedRate(() -> {
                try {
                    updateChat();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 0, 1, TimeUnit.SECONDS).get();
        } catch (InterruptedException | ExecutionException e) {
            if (e.getCause() instanceof SocketException) {
                executor.shutdownNow();
            } else {
                e.printStackTrace();
            }
        }
    }

    public void updateChat() throws Exception {
        processChatTokens(getChatTokens());
    }

    public void processChatTokens(Set<Chat.ChatUpdate> updates) {
        Platform.runLater(() -> {
            for (Chat.ChatUpdate update : updates) {
                if (update.wasAdded) {
                    output.getItems().add(update.message.content);
                } else if (update.wasRemoved) {
                    output.getItems().remove(update.message.content);
                } else {
                    throw new IllegalArgumentException("Invalid update: " + update);
                }
            }
        });
    }

    public Set<Chat.ChatUpdate> getChatTokens() throws Exception {
        List<?> updateTokens = getClient().queryObject(new Request("CHAT"), List.class);
        Set<Chat.ChatUpdate> updates = new HashSet<>();
        if (!updateTokens.isEmpty()) {
            updates.addAll(updateTokens.stream()
                    .filter(new TypePredicate<>(Chat.ChatUpdate.class))
                    .map(new TypeFunction<>(Chat.ChatUpdate.class))
                    .collect(Collectors.toSet()));
        }
        return updates;
    }
}
