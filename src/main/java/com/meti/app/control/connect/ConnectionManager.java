package com.meti.app.control.connect;

import com.meti.app.control.InfinityController;
import com.meti.lib.net.Connection;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/29/2019
 */
public class ConnectionManager extends InfinityController implements Initializable {
    private final ObjectProperty<Connection<?, ?, ?>> currentConnection = new SimpleObjectProperty<>();
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

    //can't have wildcard here because there's apparently an inequality constraint
    private Map<String, ConnectionCreator<?>> creatorMap;

    @FXML
    public void addConnection() {
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
            if (newValue == null) {
                setContentAccessible(false);
            } else {
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
    public void confirmInfinity() {
        moduleManager.instancesOf(ConnectionCreator.class)
                .forEach(connectionCreator -> creatorMap.put(connectionCreator.getName(), connectionCreator));
    }
}