package com.meti.app;

import com.meti.lib.fx.Controller;
import com.meti.lib.fx.ControllerLoader;
import com.meti.lib.fx.PostInitializable;
import com.meti.lib.fx.TreeBuilder;
import com.meti.lib.net.client.Client;
import com.meti.lib.net.command.GetCommand;
import com.meti.lib.collect.CollectionUtil;
import com.meti.lib.convert.StringConverter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientDisplay extends Controller implements PostInitializable  {
    @FXML
    private TreeView<String> fileView;

    @FXML
    private TabPane tabPane;

    @FXML
    public void exit(){
        Platform.exit();
    }

    @Override
    public void postInitialize() {
        try {
            loadClient(state.getClient());
            loadTabs();
        } catch (Exception e) {
            state.getLogger(ClientDisplay.this.getClass()).error("Failed to loadToParent client", e);
        }
    }

    private void loadTabs() throws IOException {
        loadChat();
    }

    private void loadChat() throws IOException {
        Tab chatTab = new Tab("Chat", ControllerLoader.loadToParent(getClass().getResource("/com/meti/app/Chat.fxml"), state));
        tabPane.getTabs().add(chatTab);
    }

    private void loadClient(Client<?> client) throws Exception {
        ArrayList<?> fileTokens = client.runReturnableCommand(new GetCommand<>(CollectionUtil.asArrayList("files"), ArrayList.class));

        List<String> files = CollectionUtil.convert(fileTokens, new StringConverter());
        buildTree(files);
    }

    private void buildTree(List<String> files) {
        TreeItem<String> root = new StringFileTreeBuilder().buildTree(files);
        fileView.setShowRoot(false);
        fileView.setRoot(root);
    }

    private static class StringFileTreeBuilder extends TreeBuilder<String> {
        @Override
        public TreeItem<String> detectParent(String parent, String child) {
            return new TreeItem<>(child.replace(parent + "\\", ""));
        }

        @Override
        public boolean isParent(String parent, String child) {
            return child.startsWith(parent);
        }

        @Override
        public TreeItem<String> createTreeItem(String value) {
            return new TreeItem<>(value);
        }
    }
}
