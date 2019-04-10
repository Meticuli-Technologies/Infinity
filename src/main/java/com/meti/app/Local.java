package com.meti.app;

import com.meti.lib.collect.State;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Local extends InfinityController {
    @FXML
    private TextField portField;

    public Local(State state, Stage stage) {
        super(state, stage);
    }

    @FXML
    public void back() {
        factory.accept(() -> onto(URLS.getMenuURL().openStream()));
    }

    @FXML
    public void next() {

    }
}
