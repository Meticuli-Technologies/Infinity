package com.meti.app.server;

import com.meti.app.*;
import com.meti.lib.asset.manage.AssetManager;
import com.meti.lib.asset.source.PathSource;
import com.meti.lib.asset.text.TextAssetTranslator;
import com.meti.lib.collect.State;
import com.meti.lib.javafx.InjectorLoader;
import com.meti.lib.javafx.StageManager;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.Closeable;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/3/2019
 */
public class ServerApp extends Application {
    private static final Path ASSET_PATH = Paths.get(".\\assets");
    private final Controls controls;
    private final State state;
    private final Toolkit toolkit;

    public ServerApp() {
        state = new InfinityState();
        toolkit = new StateBasedToolkit(state);
        controls = new ControlsImpl(state, toolkit);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AssetManager assetManager = toolkit.getAssetManager();
        loadAssetManager(assetManager);
        loadServerMenu(primaryStage, toolkit.getStageManager());
    }

    private void loadAssetManager(AssetManager assetManager) throws IOException {
        ensureAssetPath();
        assetManager.addTranslator(new TextAssetTranslator());
        assetManager.read(new PathSource(ASSET_PATH));
    }

    private void ensureAssetPath() throws IOException {
        if (!Files.exists(ASSET_PATH)) {
            Files.createDirectory(ASSET_PATH);
        }
    }

    private void loadServerMenu(Stage primaryStage, StageManager stageManager) throws java.io.IOException {
        Parent parent = InjectorLoader.load(List.of(controls), getServerMenuURL());
        stageManager.addStage(primaryStage);
        stageManager.loadPrimaryStage(parent);
    }

    private URL getServerMenuURL() {
        return getClass().getResource("/com/meti/app/server/ServerMenu.fxml");
    }


    @Override
    public void stop() {
        closeCloseables(state);
    }

    private void closeCloseables(State state) {
        Set<Closeable> closeables = state.filterByClass(Closeable.class);
        for (Closeable closeable : closeables) {
            tryClose(closeable);
        }
    }

    private void tryClose(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
