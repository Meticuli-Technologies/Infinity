package com.meti;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class Menu {
    @FXML
    private TextField portField;

    @FXML
    public void exit(){
        Platform.exit();
    }

    @FXML
    public void next(){

    }
}
