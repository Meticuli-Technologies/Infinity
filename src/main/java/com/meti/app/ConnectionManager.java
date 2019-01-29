package com.meti.app;

import com.meti.lib.fx.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/29/2019
 */
public class ConnectionManager extends Controller {
    @FXML
    private ListView<String> connectionWizardsView;

    @FXML
    private ListView<String> currentConnectionsView;
}
