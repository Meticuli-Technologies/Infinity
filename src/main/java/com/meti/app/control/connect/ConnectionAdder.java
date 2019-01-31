package com.meti.app.control.connect;

import com.meti.app.control.InfinityController;
import com.meti.lib.fx.Wizard;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.Optional;

public class ConnectionAdder extends InfinityController {
    @FXML
    private ListView<String> wizardNameList;

    @FXML
    public void back() {

    }

    @FXML
    public void next() {

    }

    @Override
    public Optional<Class<? extends Wizard>> getWizardClass() {
        return Optional.of(ConnectionCreator.class);
    }

    @Override
    public void confirm() {
        super.confirm();

        wizardNameList.getItems().addAll(wizards.keySet());
    }
}
