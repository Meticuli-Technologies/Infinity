package com.meti;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class ClientDisplay extends InfinityClientController {
    @FXML
    private ListView<String> chatListView;

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
}
