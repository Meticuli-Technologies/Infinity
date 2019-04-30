package com.meti.app.control;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/30/2019
 */
public class ClientDisplay {
    @FXML
    private Text addressText;

    @FXML
    private Text portText;

    @FXML
    private ListView<String> viewListView;
}
