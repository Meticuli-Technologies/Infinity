package com.meti.app;

import com.meti.lib.fx.Controller;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class Display extends Controller {
    @FXML
    private AnchorPane contentPane;

    @FXML
    public void exit() {
        Platform.exit();
    }

    @FXML
    public void openNewConnection() {
        try {
            load(newConnectionFXML());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public URL newConnectionFXML() {
        return getClass().getResource("/com/meti/app/NewConnection.fxml");
    }
}
