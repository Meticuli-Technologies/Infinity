package com.meti;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class ServerDisplay extends InfinityServerController {

    @FXML
    private ListView<String> clientListView;

    @FXML
    private ListView<String> chatListView;

    public ServerDisplay(State state) {
        super(state);
    }
}
