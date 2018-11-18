package com.meti.app;

import com.meti.lib.fx.Controller;
import com.meti.lib.fx.PostInitializable;
import com.meti.lib.net.client.Client;
import com.meti.lib.net.command.GetCommand;
import com.meti.lib.collect.CollectionUtil;
import com.meti.lib.convert.StringConverter;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ClientDisplay extends Controller implements PostInitializable  {
    @FXML
    private TreeView<String> fileView;

    @Override
    public void postInitialize() {
        try {
            loadClient(state.getClient());
        } catch (Exception e) {
            state.getLogger(ClientDisplay.this.getClass()).error("Failed to load client", e);
        }
    }

    private void loadClient(Client<?> client) throws Exception {
        String fileDirectory = client.runReturnableCommand(new GetCommand<>(CollectionUtil.asArrayList("fileDirectory"), String.class));
        ArrayList<?> fileTokens = client.runReturnableCommand(new GetCommand<>(CollectionUtil.asArrayList("files"), ArrayList.class));

        List<String> files = CollectionUtil.convert(fileTokens, new StringConverter());
        buildTree(fileDirectory, files);
    }

    private void buildTree(String fileDirectory, List<String> files) {
        TreeItem<String> root = new TreeItem<>(null);
        fileView.setShowRoot(false);
        fileView.setRoot(root);

        Map<String, TreeItem<String>> treeItemMap = new HashMap<>();
        files.forEach(path1 -> files.forEach(new TreeBuilder(treeItemMap, path1)));
        files.stream()
                .filter(path -> path.equals(fileDirectory))
                .forEach(path -> root.getChildren().add(treeItemMap.get(path)));
    }

    private static class TreeBuilder implements Consumer<String> {
        private final Map<String, TreeItem<String>> treeItemMap;
        private final String path1;

        TreeBuilder(Map<String, TreeItem<String>> treeItemMap, String path1) {
            this.treeItemMap = treeItemMap;
            this.path1 = path1;
        }

        @Override
        public void accept(String path2) {
            if (!treeItemMap.containsKey(path1)) {
                createTreeItem(path1);
            }

            if (!path1.equals(path2)) {
                if (isParent(path1, path2)) {
                    createTreeItem(path1, path2).getChildren().add(createTreeItem(path2, path1));
                } else {
                    if (!treeItemMap.containsKey(path2)) {
                        createTreeItem(path2);
                    }
                }
            }
        }

        TreeItem<String> createTreeItem(String path) {
            return createTreeItem(path, null);
        }

        boolean isParent(String parent, String child) {
            return child.startsWith(parent);
        }

        TreeItem<String> createTreeItem(String path, String parent) {
            if (treeItemMap.containsKey(path)) {
                return treeItemMap.get(path);
            } else {
                String[] args = path.split("/");
                String arg = args[args.length - 1];
                if (parent != null) {
                    arg = arg.replace(parent + "\\", "");
                }
                TreeItem<String> item = new TreeItem<>(arg);
                treeItemMap.put(path, item);
                return item;
            }
        }
    }
}
