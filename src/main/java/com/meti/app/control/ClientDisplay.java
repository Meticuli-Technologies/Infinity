package com.meti.app.control;

import com.meti.app.core.state.Toolkit;
import com.meti.lib.mod.ModManagerImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
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
        assetView.getSelectionModel().getSelectedItems().forEach(new Consumer<TreeItem<String>>() {
            @Override
            public void accept(TreeItem<String> stringTreeItem) {
                stringTreeItem.getValue();
            }
        });

        try {
            loadEditors(toolkit.getModManager());
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            toolkit.getLogger().log(Level.WARNING, "Failed to load editors", e);
        }
    }

    private void loadEditors(ModManagerImpl modManager) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        editors.addAll(modManager.getAllInstances(Editor.class, new ArrayList<>()));
    }
}
