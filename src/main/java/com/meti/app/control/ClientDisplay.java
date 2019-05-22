package com.meti.app.control;

import com.meti.app.core.state.Toolkit;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/22/2019
 */
public class ClientDisplay implements Initializable {
    @FXML
    private TreeView<String> assetView;

    private final Toolkit toolkit;

    public ClientDisplay(Toolkit toolkit) {
        this.toolkit = toolkit;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toolkit.getClient();
    }
}
