package com.meti.app.control;

import com.meti.app.core.InfinityController;
import com.meti.lib.collection.State;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/31/2019
 */
public class Local extends InfinityController {
    @FXML
    private TextField portField;

    public Local(State state) {
        super(state);
    }

    @FXML
    public void back() {
        try {
            console.log(Level.INFO, "Returning to Menu.");
            onto(getClass().getResource("/com/meti/app/control/Menu.fxml"));
        } catch (IOException e) {
            console.log(Level.WARNING, e);
        }
    }

    @FXML
    public void next() {

    }
}
