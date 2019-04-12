package com.meti.app.control;

import com.meti.lib.asset.Asset;
import com.meti.lib.asset.AssetEvent;
import com.meti.lib.asset.AssetUpdate;
import com.meti.lib.util.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TreeView;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
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

    public Files(State state) {
        super(state);
    }

    @FXML
    public void index() {
        try {
            DirectoryChooser chooser = new DirectoryChooser();
            Stage stage = state.byClass(Stage.class)
                    .findAny()
                    .orElse(new Stage());
            Path path = chooser.showDialog(stage).toPath();
            server.assetManager.load(path);
        } catch (IOException e) {
            console.log(Level.WARNING, e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        server.assetManager.eventManager.put(AssetEvent.ON_INDEX, new Consumer<AssetUpdate>() {
            @Override
            public void accept(AssetUpdate assetUpdate) {
                Asset asset = assetUpdate.asset;
                indexAsset(asset);
            }

            private void indexAsset(Asset asset) {
                if(fileView.getRoot()
            }

            private Asset findParent(Asset asset){
                fileView.getRoot().getChildren().contains(asset.getParent());
            }
        });
    }
}

