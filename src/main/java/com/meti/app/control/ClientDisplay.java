package com.meti.app.control;

import com.meti.app.core.state.Toolkit;
import com.meti.lib.asset.Asset;
import com.meti.lib.asset.AssetManagerImpl;
import com.meti.lib.mod.ModManagerImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;
import java.util.function.Consumer;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/22/2019
 */
public class ClientDisplay implements Initializable {
    @FXML
    private TreeView<String> assetView;

    private final Set<Editor> editors = new HashSet<>();

    @FXML
    private ContextMenu editorMenu;

    private final Toolkit toolkit;

    public ClientDisplay(Toolkit toolkit) {
        this.toolkit = toolkit;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TreeItem<String> root = indexAssetManager(toolkit.getAssetManager());
        assetView.setRoot(root);
    /*    assetView.getSelectionModel().getSelectedItems().forEach(new Consumer<TreeItem<String>>() {
            @Override
            public void accept(TreeItem<String> stringTreeItem) {
                String value = stringTreeItem.getValue();
            }
        });*/

        try {
            loadEditors(toolkit.getModManager());
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            toolkit.getLogger().log(Level.WARNING, "Failed to load editors", e);
        }
    }

    private void indexAsset(Asset asset, TreeItem<String> root, Map<Asset, TreeItem<String>> parentMap) {
        TreeItem<String> assetItem = new TreeItem<>(asset.getName());
        Optional<Asset> parentOptional = asset.getParent();
        if (parentOptional.isPresent()) {
            indexParent(root, parentMap, assetItem, parentOptional.get());
        } else {
            root.getChildren().add(assetItem);
        }
        parentMap.put(asset, assetItem);
    }

    private TreeItem<String> indexAssetManager(AssetManagerImpl assetManager) {
        Set<Asset> assets = assetManager.getAssets();
        TreeItem<String> root = new TreeItem<>("root");
        Map<Asset, TreeItem<String>> parentMap = new HashMap<>();
        for (Asset asset : assets) {
            indexAsset(asset, root, parentMap);
        }
        return root;
    }

    private void indexParent(TreeItem<String> root, Map<Asset, TreeItem<String>> parentMap, TreeItem<String> assetItem, Asset parentAsset) {
        if (parentMap.containsKey(parentAsset)) {
            parentMap.get(parentAsset).getChildren().add(assetItem);
        } else {
            indexAsset(parentAsset, root, parentMap);
        }
    }

    private void loadEditors(ModManagerImpl modManager) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        editors.addAll(modManager.getAllInstances(Editor.class, new ArrayList<>()));
    }
}
