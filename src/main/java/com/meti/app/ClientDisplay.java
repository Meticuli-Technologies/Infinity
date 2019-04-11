package com.meti.app;

import com.meti.lib.util.State;
import com.meti.chat.ChatRequest;
import com.meti.chat.ChatUpdate;
import com.meti.chat.Message;
import com.meti.app.control.InfinityClientController;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class ClientDisplay extends InfinityClientController implements Initializable {
    @FXML
    private ListView<Message> chatListView;

    @FXML
    private TextField input;

    public ClientDisplay(State state) {
        super(state);
    }

    @FXML
    public void handle(){
        try {
            Message message = new Message("unknown", input.getText());
            querier.query(message).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    ChatUpdate update = (ChatUpdate) querier.query(new ChatRequest()).get();
                    if (update.latest != null) {
                        chatListView.getItems().add(update.latest);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    stop();
                }
            }
        };
        timer.start();
    }
}
