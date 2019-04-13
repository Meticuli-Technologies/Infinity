package com.meti.app.control;

import com.meti.lib.asset.Asset;
import com.meti.lib.asset.AssetEvent;
import com.meti.lib.asset.DirectoryAsset;
import com.meti.lib.util.CollectionUtil;
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

    private final Map<DirectoryAsset, TreeItem<Asset>> parentMap = new HashMap<>();
    @FXML
    private TreeView<Asset> fileView;
    private final TreeItem<Asset> root = new TreeItem<>();

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
    public void changeTo() {
        CollectionUtil.toSingle(fileView.getSelectionModel().getSelectedItems())
                .ifPresent(this::changeTo);
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

    private void changeTo(TreeItem<Asset> toSingle) {
        Asset asset = toSingle.getValue();
        nameText.setText(asset.getName());
    }

    private void indexAsset(Asset asset) {
        TreeItem<Asset> treeItem = new TreeItem<>(asset);
        if (asset instanceof DirectoryAsset) {
            DirectoryAsset directoryAsset = (DirectoryAsset) asset;
            directoryAsset.assets.forEach(this::indexAsset);
            parentMap.put(directoryAsset, treeItem);
        }

        Stream<TreeItem<Asset>> stream = parentMap.keySet()
                .stream()
                .filter(directoryAsset -> directoryAsset.assets.contains(asset))
                .map(parentMap::get)
                .peek(stringTreeItem -> stringTreeItem.getChildren().add(treeItem));

        if (stream.count() == 0) {
            root.getChildren().add(treeItem);
        }
    }
}

