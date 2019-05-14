package com.meti.net.client;

import com.meti.fx.StageManager;
import com.meti.module.InfinityModuleManager;
import com.meti.module.ModuleManager;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ClientDisplay implements Initializable {
    private final Logger logger;
    private final StageManager stageManager;
    private final ModuleManager moduleManager;
    private final Client client;
    private final InfinityClientTokenHandler handler;
    private final Map<String, ClientViewModel> models = new HashMap<>();

    @FXML
    private ListView<String> viewList;

    public ClientDisplay(Logger logger, StageManager stageManager, InfinityModuleManager moduleManager, Client client, InfinityClientTokenHandler handler) {
        this.logger = logger;
        this.stageManager = stageManager;
        this.moduleManager = moduleManager;
        this.client = client;
        this.handler = handler;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            models.putAll(moduleManager.constructInstances(ClientViewModel.class, logger, client)
                    .stream()
                    .peek(clientViewModel -> handler.addHandlers(clientViewModel.getHandlers()))
                    .collect(Collectors.toMap(ClientViewModel::getName, Function.identity())));
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
        }

        models.keySet().forEach(name -> viewList.getItems().add(name));
    }

    @FXML
    public void open() {
        ObservableList<String> views = viewList.getSelectionModel().getSelectedItems();
        Set<ClientViewModel> models = views.stream().map(this.models::get).collect(Collectors.toSet());
        for (ClientViewModel model : models) {
            try {
                Stage nextStage = stageManager.allocate();
                nextStage.setScene(new Scene(model.getRoot()));
                nextStage.show();
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }
    }
}
