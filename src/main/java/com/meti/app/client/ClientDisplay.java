package com.meti.app.client;

import com.meti.app.Controls;
import com.meti.app.InfinityController;
import com.meti.app.server.asset.AssetPropertiesRequestResponse;
import com.meti.app.server.asset.SerializedAssetPropertiesRequest;
import com.meti.lib.asset.properties.AssetProperties;
import com.meti.lib.javafx.InjectorLoader;
import com.meti.lib.javafx.StageManager;
import com.meti.lib.net.TypeHandler;
import com.meti.lib.net.client.Client;
import com.meti.lib.net.client.SocketClient;
import com.meti.lib.net.client.handle.ClientProcessor;
import com.meti.lib.net.client.handle.ResponseProcessor;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public class ClientDisplay extends InfinityController implements Initializable {
    private Client client;
    private ResponseProcessor processor;

    @FXML
    private TreeView<String> assetView;

    @FXML
    public void open(){

    }

    @FXML
    public void close() {
        Platform.exit();
    }

    public ClientDisplay(Controls controls) {
        super(controls);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Optional<ClientBootstrap> clientBootstrap = state.singleByClass(ClientBootstrap.class);
        if (clientBootstrap.isPresent()) {
            loadClient(clientBootstrap.get());
        } else {
            //TODO: do something
        }

        try {
            client.writeAndFlush(new SerializedAssetPropertiesRequest());
            processor.addHandler(new AssetPropertiesRequestResponseHandler(assetView));
            processor.processNextResponse();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void loadClient(ClientBootstrap clientBootstrap) {
        try {
            tryLoadClient(clientBootstrap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void tryLoadClient(ClientBootstrap clientBootstrap) throws IOException {
        client = new SocketClient(clientBootstrap);
        processor = new ClientProcessor(client);
        state.add(client);
        state.add(processor);
    }


    @FXML
    public void openChat(){
        try {
            StageManager stageManager = toolkit.getStageManager();
            loadChatDisplay(stageManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadChatDisplay(StageManager stageManager) throws IOException {
        Parent parent = InjectorLoader.load(List.of(getControls()), getChatDisplayURL());
        stageManager.loadPrimaryStage(parent);
    }

    private URL getChatDisplayURL() {
        return getClass().getResource("/com/meti/app/client/ChatDisplay.fxml");
    }

    @FXML
    public void openIssues(){

    }

    private static class AssetPropertiesRequestResponseHandler extends TypeHandler<AssetPropertiesRequestResponse> {
        private final Map<String, AssetProperties> propertiesByNameMap = new HashMap<>();
        private final Map<String, TreeItem<String>> itemMap = new HashMap<>();
        private final TreeItem<String> root = new TreeItem<>();
        private final TreeView<String> assetView;

        public AssetPropertiesRequestResponseHandler(TreeView<String> assetView) {
            super(AssetPropertiesRequestResponse.class);
            this.assetView = assetView;
        }

        @Override
        public Optional<Serializable> handleGeneric(AssetPropertiesRequestResponse response, Client client) {
            List<AssetProperties> properties = response.getProperties();
            indexPropertiesList(properties);
            assetView.setRoot(root);
            assetView.setShowRoot(false);
            return Optional.empty();
        }

        private void indexPropertiesList(Collection<? extends AssetProperties> propertiesList) {
            propertiesList.stream()
                    .peek(properties -> propertiesByNameMap.put(properties.getName(), properties))
                    .forEach(this::indexProperties);
        }

        private void assignParent(AssetProperties properties, TreeItem<String> item) {
            Optional<String> parentNameOptional = properties.getParentName();
            if (parentNameOptional.isPresent()) {
                indexWithParent(item, parentNameOptional.get());
            } else {
                root.getChildren().add(item);
            }
        }

        private TreeItem<String> findParentItem(String parentName) {
            return itemMap.containsKey(parentName)
                    ? itemMap.get(parentName)
                    : indexParentProperties(parentName);
        }

        private TreeItem<String> indexParentProperties(String parentName) {
            TreeItem<String> parentItem;
            AssetProperties parentProperties = propertiesByNameMap.get(parentName);
            parentItem = indexProperties(parentProperties);
            return parentItem;
        }

        private TreeItem<String> indexProperties(AssetProperties properties) {
            TreeItem<String> item = registerProperties(properties);
            assignParent(properties, item);
            return item;
        }

        private void indexWithParent(TreeItem<String> item, String parentName) {
            TreeItem<String> parentItem = findParentItem(parentName);
            parentItem.getChildren().add(item);
        }

        private TreeItem<String> registerProperties(AssetProperties properties) {
            String name = properties.getName();
            TreeItem<String> item = new TreeItem<>(name);
            itemMap.put(name, item);
            return item;
        }
    }
}
