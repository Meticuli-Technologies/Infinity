package com.meti.app.control;

import com.meti.lib.State;
import com.meti.lib.fx.Controller;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ServerMenu extends Controller {
    @FXML
    private TextField portField;

    public ServerMenu(State state) {
        super(state);
    }

    @FXML
    public void exit(){
        Platform.exit();
    }

    @FXML
    public void next(){

    }
}
