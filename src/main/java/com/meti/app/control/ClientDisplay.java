package com.meti.app.control;

import com.meti.app.control.helper.InfinityClientController;
import com.meti.lib.State;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/30/2019
 */
public class ClientDisplay extends InfinityClientController {
    @FXML
    private Text addressText;

    @FXML
    private Text portText;

    @FXML
    private ListView<String> viewListView;

    public ClientDisplay(State state) {
        super(state);
    }
}
