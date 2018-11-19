package com.meti.app;

import com.meti.lib.fx.Controller;
import com.meti.lib.fx.PostInitializable;
import com.meti.lib.net.client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/18/2018
 */
public class Chat extends Controller implements PostInitializable {
    @FXML
    private TextField input;

    @FXML
    private TextArea output;

    @Override
    public void postInitialize() {
        Client<?> client = state.getClient();

    }
}
