package com.meti.app;

import javafx.application.Platform;
import javafx.fxml.FXML;

public class Display {
    @FXML
    public void exit() {
        Platform.exit();
    }
}
