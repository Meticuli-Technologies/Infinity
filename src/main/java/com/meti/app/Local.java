package com.meti.app;

import com.meti.lib.collect.State;
import com.meti.lib.fx.FXMLBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class Local extends InfinityController {
    @FXML
    private TextField portField;

    public Local(State state, Stage stage) {
        super(state, stage);
    }

    @FXML
    public Optional<Exception> back() {
        return factory.accept(this::loadMenu).get();
    }

    FXMLBundle<Menu> loadMenu() throws IOException {
        return onto(URLS.getMenuURL().openStream());
    }

    @FXML
    public void next() {

    }
}
