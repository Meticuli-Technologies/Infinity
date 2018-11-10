package com.meti.app;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class HostALocalServer extends Controller {
    @FXML
    private TextField portField;

    @FXML
    public void back(){
        try {
            state.firstOfType(Stage.class)
                    .orElse(new Stage())
                    .setScene(new Scene(Main.load(getClass().getResource("/ServerDisplay.fxml"), state)));
        } catch (IOException e) {
            getLogger().error("", e);
        }
    }

    @FXML
    public void next(){
    }
}
