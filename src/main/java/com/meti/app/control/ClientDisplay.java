package com.meti.app.control;

import com.meti.lib.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientDisplay extends InfinityController implements Initializable {
    @FXML
    private TextArea output;

    @FXML
    private TextField input;

    public ClientDisplay(State state) {
        super(state);
    }


    @FXML
    public void handle() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        output.appendText("Successfully logged on.");
    }
}
