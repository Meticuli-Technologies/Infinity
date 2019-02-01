package com.meti.app.control.connect;

import com.meti.app.control.InfinityController;
import com.meti.lib.fx.ControllerLoader;
import com.meti.lib.net.Connection;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

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

    @FXML
    public void addConnection() {
        try {
            ControllerLoader loader = new ControllerLoader(getCreatorViewURL(), state.get());
            Parent root = loader.load();
            ((InfinityController) loader.getController()).backURL.set(getClass().getResource("/com/meti/app/control/ConnectionManager.fxml"));

            Stage allocate = stageManager.allocate();
            allocate.setScene(new Scene(root));
            allocate.showAndWait();

            ConnectionCreatorView view = loader.getController();
            view.getConnection();
        } catch (IOException e) {
            console.log(Level.WARNING, e);
        }
    }

    public URL getCreatorViewURL() {
        return getClass().getResource("/com/meti/app/control/ConnectionCreatorView.fxml");
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
        toBack();
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

        backURL.set(getClass().getResource("/com/meti/app/control/Menu.fxml"));
    }

    private void setContentAccessible(boolean b) {
        dataPane.setVisible(b);
        continueButton.setDisable(!b);
    }
}