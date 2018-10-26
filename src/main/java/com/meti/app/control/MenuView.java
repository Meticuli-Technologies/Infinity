package com.meti.app.control;

import com.meti.app.main.ClientMain;
import com.meti.lib.client.Client;
import com.meti.lib.fx.Controller;
import com.meti.lib.fx.ControllerLoader;
import com.meti.lib.fx.ControllerState;
import com.meti.lib.fx.Dependency;
import com.meti.lib.fx.depend.WindowedDependency;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.meti.lib.fx.ControllerLoader.defaultControllerState;
import static com.meti.lib.fx.ControllerLoader.loadWithDependenciesStatic;

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

    @FXML
    public void removeServer(){
        serverList.getSelectionModel().getSelectedItems().forEach(this::removeServerImpl);
    }

    public void removeServerImpl(InetAddress address) {
        try {
            Client client = ClientMain.clientState.clientMap.remove(address);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addServer() {
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(loadWithDependenciesStatic(getResource(), ControllerState.of(stage))));
            stage.show();
        } catch (Exception e) {
            ClientMain.logger.error("Failed to load fxml", e);
        }
    }

    @FXML
    public void clearServers() {
        serverList.getItems().forEach(this::removeServerImpl);
    }

    @FXML
    public void openServer(){
        //TODO: openServer
        try {
            ControllerLoader controllerLoader = new ControllerLoader(getClass().getResource("/com/meti/app/fxml/ServerView.fxml"));
            getDependency(WindowedDependency.class).sceneProperty.get().setRoot(controllerLoader.loadWithDependencies(defaultControllerState));
            ((ServerView) controllerLoader.getController()).setAddress(serverList.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            ClientMain.logger.error("Failed to load fxml", e);
        }
    }

    private URL getResource() {
        return getClass().getResource("/com/meti/app/fxml/wizard/AddServer.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ClientMain.clientState.clientMap.addListener((MapChangeListener<InetAddress, Client>) change -> {
            if (change.wasAdded()) {
                serverList.getItems().add(change.getKey());
            } else if(change.wasRemoved()){
                serverList.getItems().remove(change.getKey());
            } else {
                throw new IllegalStateException("Not sure what was changed");
            }
        });
    }
}
