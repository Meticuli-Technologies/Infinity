package com.meti.app.control;

import com.meti.lib.fx.Wizard;
import com.meti.lib.net.Connection;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/29/2019
 */
public class ConnectionManager extends InfinityController implements Initializable  {
    @FXML
    private ListView<String> connectionListView;

    @FXML
    private Text nameText;

    @FXML
    private Text statusText;

    @FXML
    private AnchorPane dataPane;

    @FXML
    private Button continueButton;

    private final ObservableList<ConnectionCreator> connectionCreators = FXCollections.observableArrayList();
    private final ObjectProperty<Connection<?, ?, ?>> currentConnection = new SimpleObjectProperty<>();

    @FXML
    public void addConnection() {
        int selectedItem = connectionListView.getSelectionModel().getSelectedIndex();
        ConnectionCreator connectionCreator = connectionCreators.get(selectedItem);
        connectionCreator.open();
    }

    @FXML
    public void closeConnection() {

    }

    @FXML
    public void clearConnections() {

    }

    @FXML
    public void reset() {

    }

    @FXML
    public void doContinue() {

    }

    @FXML
    public void back() {
        ontoBack();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentConnection.addListener((observable, oldValue, newValue) -> {
            if(newValue == null){
                setContentAccessible(false);
            }
            else{
                setContentAccessible(true);
            }
        });

        setContentAccessible(false);
    }

    private void setContentAccessible(boolean b) {
        dataPane.setVisible(b);
        continueButton.setDisable(!b);
    }

    @Override
    public void confirm() {
        wizards.keySet().forEach(s -> {
            connectionListView.getItems().add(s);
            connectionCreators.add((ConnectionCreator) wizards.get(s));
        });
    }

    @Override
    public Optional<Class<? extends Wizard>> getWizardClass() {
        return Optional.of(ConnectionCreator.class);
    }
}
