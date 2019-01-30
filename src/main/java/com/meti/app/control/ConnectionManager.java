package com.meti.app.control;

import com.meti.lib.net.Connection;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/29/2019
 */
public class ConnectionManager extends InfinityController implements Initializable  {
    @FXML
    private ListView<String> connectionList;

    @FXML
    private Text nameText;

    @FXML
    private Text statusText;

    @FXML
    private AnchorPane dataPane;

    private final ObjectProperty<Connection<?, ?, ?>> currentConnection = new SimpleObjectProperty<>();

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

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentConnection.addListener((observable, oldValue, newValue) -> {
            if(newValue == null){
                dataPane.setVisible(false);
            }
            else{
                dataPane.setVisible(true);
            }
        });

        dataPane.setVisible(false);
    }
}
