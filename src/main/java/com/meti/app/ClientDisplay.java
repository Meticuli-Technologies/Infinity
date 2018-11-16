package com.meti.app;

import com.meti.lib.fx.Controller;
import com.meti.lib.fx.PostInitializable;
import com.meti.lib.net.client.Client;
import com.meti.lib.net.command.GetCommand;
import com.meti.lib.util.ListUtil;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ClientDisplay extends Controller implements PostInitializable  {
    @FXML
    private TreeView<String> fileView;

    @Override
    public void postInitialize() {
        Optional<Client> clientOptional = state.firstOfType(Client.class);
        if(clientOptional.isPresent()){
            Client<?> client = clientOptional.get();
            try {
                loadClient(client);
            } catch (Exception e) {
                getLogger().error("Failed to load client", e);
            }
        }
        else{
            throw new IllegalStateException("Client not found");
        }
    }

    private void loadClient(Client<?> client) throws Exception {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Object directoryToken = client.runReturnableCommand(new GetCommand<>(ListUtil.asArrayList("fileDirectory"), String.class), service, Duration.ofSeconds(1));
        ArrayList<?> fileTokens = client.runReturnableCommand(new GetCommand<>(ListUtil.asArrayList("files"), ArrayList.class), service, Duration.ofSeconds(1));

        String fileDirectory = directoryToken.toString();
        List<String> files = fileTokens.stream()
                .map((Function<Object, String>) o -> o instanceof String ? o.toString() : null)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

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
