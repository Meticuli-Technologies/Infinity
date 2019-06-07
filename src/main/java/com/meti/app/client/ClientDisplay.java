package com.meti.app.client;

import com.meti.app.Controls;
import com.meti.app.InfinityController;
import com.meti.app.MapBasedQuerier;
import com.meti.app.Querier;
import com.meti.app.asset.AssetResponse;
import com.meti.app.asset.InlineAssetRequest;
import com.meti.app.server.asset.SerializedAssetPropertiesRequest;
import com.meti.lib.asset.Asset;
import com.meti.lib.asset.properties.AssetProperties;
import com.meti.lib.javafx.InjectorLoader;
import com.meti.lib.javafx.StageManager;
import com.meti.lib.module.Module;
import com.meti.lib.module.ModuleManager;
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
import java.lang.reflect.InvocationTargetException;
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
    private Map<String, TreeItem<String>> itemMap = new HashMap<>();
    private Map<Editor, Parent> editors = new HashMap<>();
    private HashMap<String, AssetProperties> propertiesByNameMap = new HashMap<>();
    private Querier querier;

    @FXML
    public void open(){
        assetView.getSelectionModel()
                .getSelectedItems()
                .forEach(stringTreeItem -> openAsset(stringTreeItem.getValue()));
    }

    private void openAsset(String assetName) {
        for (Editor editor : editors.keySet()) {
            AssetProperties properties = propertiesByNameMap.get(assetName);
            if(editor.canRender(properties)){
                try {
                    renderAssetByName(editor, assetName);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }
    }

    private void renderAssetByName(Editor editor, String assetName) throws Throwable {
        Serializable request = new InlineAssetRequest(assetName);
        client.writeAndFlush(request);
        processor.processNextResponse();
        AssetResponse assetResponse = querier.query(AssetResponse.class).get();
        Asset<?, ?> asset = assetResponse.getAsset();
        editor.render(asset);
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
        loadClientFromBootstrap();
        tryIndexAssets();
        loadEditors(toolkit.getModuleManager());
    }

    private void loadClientFromBootstrap() {
        Optional<ClientBootstrap> clientBootstrap = state.singleByClass(ClientBootstrap.class);
        if (clientBootstrap.isPresent()) {
            loadClient(clientBootstrap.get());
        } else {
            //TODO: do something
        }
    }

    private void tryIndexAssets() {
        try {
            indexAssets();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void loadEditors(ModuleManager moduleManager) {
        for (Module module : moduleManager.getModules()) {
            try {
                Set<Editor> editorInstances = module.getInstances(Editor.class, Collections.emptyList());
                putInstances(editorInstances);
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    private void putInstances(Iterable<? extends Editor> editorInstances) {
        for (Editor editor : editorInstances) {
            this.editors.put(editor, editor.getRoot());
        }
    }

    private void indexAssets() throws Throwable {
        client.writeAndFlush(new SerializedAssetPropertiesRequest());
        processor.addHandler(new AssetPropertiesRequestResponseHandler(assetView, itemMap, propertiesByNameMap));
        processor.processNextResponse();
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
        querier = new MapBasedQuerier(processor);
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
}
