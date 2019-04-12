package com.meti.app.control;

import com.meti.lib.util.State;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class Connect extends InfinityController {
    @FXML
    private TextField addressField;

    @FXML
    private TextField portField;

    public Connect(State state) {
        super(state);
    }

    @FXML
    public void back() {

    }

    @FXML
    public void next() {

    }
}
