package com.meti.app.control.connect;

import com.meti.app.control.InfinityController;
import com.meti.lib.fx.FXWizard;
import com.meti.lib.fx.Wizard;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Level;

public class ConnectionAdder extends InfinityController {
    @FXML
    private AnchorPane contentPane;

    @FXML
    private ListView<String> wizardNameList;

    private boolean showingList = true;

    @FXML
    public void back() {
        if (showingList) {
            try {
                onto(getClass().getResource("/com/meti/app/control/ConnectionManager.fxml"));
            } catch (IOException e) {
                console.log(Level.WARNING, e);
            }
        }
        else{
            FXWizard<?> selectedWizard = (FXWizard<?>) wizards.get(wizardNameList.getSelectionModel().getSelectedItem());
            selectedWizard.open((Consumer<Parent>) this::changeContent);
        }
    }

    public void changeContent(Node node){
        this.showingList = node instanceof ListView<?>;
        ObservableList<Node> children = contentPane.getChildren();
        children.clear();
        children.add(node);
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
