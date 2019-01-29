package com.meti.app;

import com.meti.lib.fx.Controller;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/19/2018
 */
public class Display extends Controller<InfinityState> {
    @FXML
    private TabPane contentPane;

    @FXML
    public void findAConnection() {

    }

    @FXML
    public void hostAServer() {

    }

    @FXML
    public void createALocalServer() {

    }

    @FXML
    public void exit() {
        Platform.exit();
    }
}
