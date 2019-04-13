package com.meti.app.control;

import com.meti.lib.asset.Asset;
import com.meti.lib.asset.AssetEvent;
import com.meti.lib.asset.AssetUpdate;
import com.meti.lib.util.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Level;

public class Files extends InfinityServerController implements Initializable {
    @FXML
    private Text nameText;

    @FXML
    private Text sizeText;

    @FXML
    private Text lastAccessedText;

    @FXML
    private TreeView<Asset> fileView;

    private final TreeItem<Asset> root = new TreeItem<>();
    private final Map<Asset, TreeItem<Asset>> assetTreeItemMap = new HashMap<>();

    public Files(State state) {
        super(state);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        server.assetManager.eventManager.put(AssetEvent.ON_INDEX, assetUpdate -> indexAsset(assetUpdate.asset));
        server.assetManager.assets.forEach(this::indexAsset);
        fileView.setRoot(root);
    }

    @FXML
    public void index() {
        try {
            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setInitialDirectory(Paths.get(".\\").toFile());
            Stage stage = state.byClass(Stage.class)
                    .findAny()
                    .orElse(new Stage());
            Path path = chooser.showDialog(stage).toPath();
            server.assetManager.load(path);
        } catch (IOException e) {
            console.log(Level.WARNING, e);
        }
    }

    public void indexAsset(Asset asset) {
        TreeItem<Asset> treeItem = new TreeItem<>(asset);
        Asset parent = asset.getParent();
        if (parent == null) {
            root.getChildren().add(treeItem);
        } else {
            if (!assetTreeItemMap.containsKey(parent)) {
                indexAsset(parent);
            }

            assetTreeItemMap.get(parent).getChildren().add(treeItem);
        }

        assetTreeItemMap.put(asset, treeItem);
    }
}

