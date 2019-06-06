package com.meti.app.server;

import com.meti.app.*;
import com.meti.lib.collect.State;
import com.meti.lib.javafx.InjectorLoader;
import com.meti.lib.javafx.StageManager;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.Closeable;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/3/2019
 */
public class ServerApp extends Application {
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
        loadServerMenu(primaryStage, toolkit.getStageManager());
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
