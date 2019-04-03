package com.meti.app.control;

import com.meti.app.core.ClientInfinityController;
import com.meti.app.server.ChatEvent;
import com.meti.app.server.ChatRequest;
import com.meti.lib.collection.State;
import com.meti.lib.collection.TypeFunction;
import com.meti.lib.net.query.Update;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.logging.Level;

public class ClientDisplay extends ClientInfinityController implements Initializable {

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
            if (o instanceof Exception) {
                throw (Exception) o;
            }
            input.clear();
        } catch (Exception e) {
            console.log(Level.WARNING, e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    Object token = querier.query(new ChatRequest(ChatEvent.ADDED))
                            .handle(console.biFunction())
                            .get();

                    if (token instanceof Update) {
                        Collection<?> content = ((Update) token).content;
                        if (!content.isEmpty()) {
                            content.parallelStream()
                                    .map(new TypeFunction<>(ChatEvent.class))
                                    .map((Function<ChatEvent, Runnable>) chatEvent -> () -> {
                                        if (chatEvent.wasAdded()) {
                                            output.getItems().add(chatEvent.getMessage().toString());
                                        } else if (chatEvent.wasRemoved()) {
                                            output.getItems().remove(chatEvent.getMessage().toString());
                                        } else {
                                            console.log(Level.WARNING, "Invalid chat event: " + chatEvent.toString());
                                        }
                                    }).forEach(Platform::runLater);
                        }
                    } else {
                        throw new IllegalStateException("Token is not instance of Update");
                    }
                } catch (Exception e) {
                    console.log(Level.WARNING, e);
                    stop();
                }
            }
        };

        timer.start();
    }
}
