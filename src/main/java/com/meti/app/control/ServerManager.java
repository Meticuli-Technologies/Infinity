package com.meti.app.control;

import com.meti.lib.fx.ControllerLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/2/2019
 */
public class ServerManager extends InfinityController implements Initializable {
    @FXML
    private AnchorPane dataPane;

    @FXML
    private ListView<String> serverListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backURL.set(getClass().getResource("/com/meti/app/control/Menu.fxml"));
    }

    @FXML
    public void addServer() {
        try {
            ControllerLoader loader = new ControllerLoader(getURL("/com/meti/app/control/ServerCreatorView.fxml"), state.get());
            Parent root = loader.load();
            ((InfinityController) loader.getController()).backURL.set(getURL("/com/meti/app/control/ServerManager.fxml"));

            Stage allocate = stageManager.allocate();
            allocate.setScene(new Scene(root));
            allocate.showAndWait();

            ServerCreatorView view = loader.getController();
            view.getServer();
        } catch (IOException e) {
            console.log(Level.WARNING, e);
        }
    }

    @FXML
    public void closeServer() {

    }

    @FXML
    public void clearServers() {

    }

    @FXML
    public void doContinue() {

    }

    @FXML
    public void back() {
        toBack();
    }

    public URL getURL(String s) {
        return getClass().getResource(s);
    }
}
