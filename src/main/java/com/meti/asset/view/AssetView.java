package com.meti.asset.view;

import com.meti.net.client.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public class AssetView implements Initializable {
    @FXML
    private TreeView<String> assetTreeView;

    private final Logger logger;
    private final Client client;

    public AssetView(Logger logger, Client client) {
        this.logger = logger;
        this.client = client;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
