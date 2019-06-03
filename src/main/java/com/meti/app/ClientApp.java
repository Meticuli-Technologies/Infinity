package com.meti.app;

import com.meti.lib.collect.State;
import com.meti.lib.fx.InjectorLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.Closeable;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/1/2019
 */
public class ClientApp extends Application {
    private final Controls controls;

    public ClientApp() {
        State state = new InfinityState();
        this.controls = new ControlsImpl(state, new StateToolkit(state));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent = InjectorLoader.load(List.of(controls), getClientMenuURL());
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }

    @Override
    public void stop() {
        closeCloseables(controls.getState());
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

    private URL getClientMenuURL() {
        return getClass().getResource("/com/meti/app/client/ClientMenu.fxml");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
