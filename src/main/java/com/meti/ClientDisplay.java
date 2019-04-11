package com.meti;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

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

    }
}
