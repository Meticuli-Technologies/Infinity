package com.meti.app.control;

import com.meti.app.ConnectionManagerWizard;
import com.meti.lib.fx.Controller;
import com.meti.lib.fx.Wizard;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.Optional;

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

    @Override
    public Optional<Class<? extends Wizard>> getWizardClass() {
        return Optional.of(ConnectionManagerWizard.class);
    }
}
