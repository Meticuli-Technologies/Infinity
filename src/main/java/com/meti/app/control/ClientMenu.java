package com.meti.app.control;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ClientMenu {
    @FXML
    private TextField addressField;

    @FXML
    private TextField portField;

    @FXML
    public void exit(){
        Platform.exit();
    }

    @FXML
    public void next(){
        //TODO: implement next
    }
}
