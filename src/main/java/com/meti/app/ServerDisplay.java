package com.meti.app;

import com.meti.lib.fx.PostInitializable;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class ServerDisplay implements PostInitializable {
    @FXML
    private TextField inputField;

    @FXML
    private TextArea outputArea;

    @FXML
    public void checkEnter(KeyEvent event){
        if(event.getCode().equals(KeyCode.ENTER)){
            parseToken(inputField.getText());
        }
    }

    private void parseToken(String text) {
        //TODO: implement ServerDisplay.parseToken(String text)
    }

    @Override
    public void postInitialize() {

    }
}
