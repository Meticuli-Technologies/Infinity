package com.meti.app.control.connect;

import com.meti.app.control.InfinityController;
import com.meti.lib.fx.ControllerLoader;
import com.meti.lib.fx.ExternalFXML;
import com.meti.lib.net.Connection;
import com.meti.lib.util.FXUtil;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.util.function.Supplier;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/31/2019
 */
public class ConnectionCreatorView extends InfinityController {
    @FXML
    private AnchorPane contentPane;

    @FXML
    private ListView<String> creatorNameList;

    private ObservableMap<String, ConnectionCreator<?>> creatorMap = FXCollections.observableHashMap();
    private boolean listVisible = true;

    @Override
    public void confirmInfinity() {
        creatorMap.addListener((MapChangeListener<String, ConnectionCreator<?>>) change -> {
            ObservableList<String> items = creatorNameList.getItems();
            if (change.wasAdded()) {
                items.add(change.getKey());
            }
            if (change.wasRemoved()) {
                items.remove(change.getKey());
            }
        });

        moduleManager.instancesOf(ConnectionCreator.class)
                .forEach(connectionCreator -> creatorMap.put(connectionCreator.getName(), connectionCreator));
    }

    @FXML
    public void back() {
        if (listVisible) {
            toBack();
        } else {
            contentPane.getChildren().clear();
            contentPane.getChildren().add(creatorNameList);
        }
    }

    @FXML
    public void next() {
        if (listVisible) {
            try {
                String selectedItem = creatorNameList.getSelectionModel().getSelectedItem();
                Node root = ControllerLoader.load(creatorMap.get(selectedItem), state.get());
                FXUtil.zeroAnchors(root);

                contentPane.getChildren().clear();
                contentPane.getChildren().add(root);
            } catch (Exception e) {
                console.log(Level.WARNING, e);
            }
        } else {
            //TODO: to display
        }
    }

    /**
     * @author SirMathhman
     * @version 0.0.0
     * @since 1/31/2019
     */
    public interface ConnectionCreator<C extends Connection<?, ?, ?>> extends ExternalFXML, Supplier<C> {
        String getName();
    }
}
