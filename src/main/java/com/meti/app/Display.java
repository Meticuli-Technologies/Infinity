package com.meti.app;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/19/2018
 */
public class Display {
    @FXML
    private TabPane contentPane;

    @FXML
    public void exit() {
        Platform.exit();
    }
}
