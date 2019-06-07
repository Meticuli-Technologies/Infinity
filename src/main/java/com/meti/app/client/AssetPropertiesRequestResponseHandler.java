package com.meti.app.client;

import com.meti.app.server.asset.AssetPropertiesRequestResponse;
import com.meti.lib.asset.properties.AssetProperties;
import com.meti.lib.net.TypeHandler;
import com.meti.lib.net.client.Client;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.Serializable;
import java.util.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/7/2019
 */
class AssetPropertiesRequestResponseHandler extends TypeHandler<AssetPropertiesRequestResponse> {
    private final Map<String, AssetProperties> propertiesByNameMap = new HashMap<>();
    private final Map<? super String, TreeItem<String>> itemMap;
    private final TreeItem<String> root = new TreeItem<>();
    private final TreeView<String> assetView;

    public AssetPropertiesRequestResponseHandler(TreeView<String> assetView, Map<? super String, TreeItem<String>> itemMap) {
        super(AssetPropertiesRequestResponse.class);
        this.assetView = assetView;
        this.itemMap = itemMap;
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
