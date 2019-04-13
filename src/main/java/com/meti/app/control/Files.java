package com.meti.app.control;

import com.meti.lib.asset.Asset;
import com.meti.lib.asset.AssetEvent;
import com.meti.lib.asset.DirectoryAsset;
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
import java.util.logging.Level;
import java.util.stream.Stream;

public class Files extends InfinityServerController implements Initializable {
    @FXML
    private Text nameText;

    @FXML
    private Text sizeText;

    @FXML
    private Text lastAccessedText;

    @FXML
    private TreeView<String> fileView;

    private final TreeItem<String> root = new TreeItem<>();
    private final Map<DirectoryAsset, TreeItem<String>> parentMap = new HashMap<>();

    public Files(State state) {
        super(state);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        server.assetManager.eventManager.put(AssetEvent.ON_INDEX, assetUpdate -> indexAsset(assetUpdate.asset));
        server.assetManager.assets.forEach(this::indexAsset);
        fileView.setShowRoot(false);
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
        TreeItem<String> treeItem = new TreeItem<>(asset.getName());
        if (asset instanceof DirectoryAsset) {
            DirectoryAsset directoryAsset = (DirectoryAsset) asset;
            directoryAsset.assets.forEach(this::indexAsset);
            parentMap.put(directoryAsset, treeItem);
        }

        Stream<TreeItem<String>> stream = parentMap.keySet()
                .stream()
                .filter(directoryAsset -> directoryAsset.assets.contains(asset))
                .map(parentMap::get)
                .peek(stringTreeItem -> stringTreeItem.getChildren().add(treeItem));

        if (stream.count() == 0) {
            root.getChildren().add(treeItem);
        }

 /*       Asset parent = asset.getParent();
        if (parent == null) {
            root.getChildren().add(treeItem);
        } else {
            if (!parentMap.containsKey(parent)) {
                indexAsset(parent);
            }

            parentMap.get(parent).getChildren().add(treeItem);
        }

        parentMap.put(asset, treeItem);*/
    }
}

