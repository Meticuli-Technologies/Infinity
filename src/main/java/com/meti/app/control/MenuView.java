package com.meti.app.control;

import com.meti.lib.client.Client;
import com.meti.lib.fx.Controller;
import com.meti.lib.fx.ControllerLoader;
import com.meti.lib.fx.Dependency;
import com.meti.lib.fx.FXUtil;
import com.meti.lib.fx.depend.WindowedDependency;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.meti.app.main.ClientMain.clientState;
import static com.meti.app.main.ClientMain.logger;
import static com.meti.lib.fx.ControllerLoader.loadWithDependenciesStatic;
import static com.meti.lib.fx.ControllerState.of;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/21/2018
 */
public class MenuView extends Controller implements Initializable {
    @FXML
    private ListView<InetAddress> serverList;

    @Override
    public Set<Class<? extends Dependency>> getDependencyClasses() {
        return Stream.of(WindowedDependency.class).collect(Collectors.toSet());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clientState.clientMap.addListener((MapChangeListener<InetAddress, Client>) change -> updateItems(change, serverList.getItems()));
    }

    private void updateItems(MapChangeListener.Change<? extends InetAddress, ? extends Client> change, ObservableList<InetAddress> items) {
        if (change.wasAdded()) {
            items.add(change.getKey());
        } else if (change.wasRemoved()) {
            items.remove(change.getKey());
        } else {
            throw new IllegalStateException("Not sure what was changed");
        }
    }

    @FXML
    public void removeServer(){
        serverList.getSelectionModel().getSelectedItems().forEach(address -> removeServerImpl(address, clientState.clientMap));
    }

    public void removeServerImpl(InetAddress address, ObservableMap<InetAddress, Client> clientMap) {
        try {
            clientMap.remove(address).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clearServers() {
        serverList.getItems().forEach(address -> removeServerImpl(address, clientState.clientMap));
    }

    @FXML
    public void addServer() {
        try {
            Stage stage = new Stage();
            FXUtil.onto(loadWithDependenciesStatic(getAddServerFXML(), of(stage)), stage);
        } catch (Exception e) {
            logger.error("Failed to load fxml", e);
        }
    }

    private URL getAddServerFXML() {
        return getClass().getResource("/com/meti/app/fxml/wizard/AddServer.fxml");
    }

    @FXML
    public void openServer(){
        try {
            ControllerLoader controllerLoader = new ControllerLoader(getClass().getResource("/com/meti/app/fxml/ServerView.fxml"));
            Parent parent = controllerLoader.loadWithDependencies();
            ServerView controller = controllerLoader.getController();

            getDependency(WindowedDependency.class).setRoot(parent);
            controller.reload(serverList.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            logger.error("Failed to load fxml", e);
        }
    }
}
