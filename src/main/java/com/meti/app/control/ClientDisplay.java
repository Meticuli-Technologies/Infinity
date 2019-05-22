package com.meti.app.control;

import com.meti.app.core.state.Toolkit;
import com.meti.lib.asset.Asset;
import com.meti.lib.asset.AssetManagerImpl;
import com.meti.lib.mod.ModManagerImpl;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/22/2019
 */
public class ClientDisplay implements Initializable {
    @FXML
    private TreeView<String> assetView;
    private final Map<Asset, TreeItem<String>> assetMap = new HashMap<>();
    @FXML
    private ContextMenu editorMenu;
    private final Map<String, Editor> editorMap = new HashMap<>();
    private final Toolkit toolkit;

    public ClientDisplay(Toolkit toolkit) {
        this.toolkit = toolkit;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TreeItem<String> root = indexAssetManager(toolkit.getAssetManager());
        assetView.setRoot(root);
        assetView.getSelectionModel().getSelectedItems().addListener(new AssetChangeListener(assetMap, editorMap.values(), editorMenu));

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
        for (Asset asset : assets) {
            indexAsset(asset, root, assetMap);
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
        Set<Editor> instances = modManager.getAllInstances(Editor.class, new ArrayList<>());
        instances.forEach(editor -> editorMap.put(editor.getName(), editor));
    }

    private static class AssetChangeListener implements ListChangeListener<TreeItem<String>> {
        private final Map<Asset, TreeItem<String>> assetMap;
        private final Collection<Editor> editors;
        private final ContextMenu editorMenu;

        public AssetChangeListener(Map<Asset, TreeItem<String>> assetMap, Collection<Editor> editors, ContextMenu editorMenu) {
            this.assetMap = assetMap;
            this.editors = editors;
            this.editorMenu = editorMenu;
        }

        @SuppressWarnings("ParameterNameDiffersFromOverriddenParameter")
        @Override
        public void onChanged(Change<? extends TreeItem<String>> change) {
            Set<String> assetNames = getAssetNames(change);
            Set<Class<?>> assetClasses = getAssetClasses(assetNames);
            Set<MenuItem> editorMenuItems = getEditorsAsMenuItems(assetClasses);
            editorMenu.getItems().addAll(editorMenuItems);
        }

        private Set<String> getAssetNames(Change<? extends TreeItem<String>> change) {
            return change.getList().stream()
                    .map(TreeItem::getValue)
                    .collect(Collectors.toSet());
        }

        private Set<Class<?>> getAssetClasses(Collection<String> assetNames) {
            return assetMap.keySet()
                    .stream()
                    .filter(asset -> assetNames.contains(asset.getName()))
                    .map(Object::getClass)
                    .collect(Collectors.toSet());
        }

        private Set<MenuItem> getEditorsAsMenuItems(Set<Class<?>> assetClasses) {
            return editors.stream()
                    .filter(editor -> editor.canShowAll(assetClasses))
                    .map(Editor::getName)
                    .map(MenuItem::new)
                    .collect(Collectors.toSet());
        }
    }
}
