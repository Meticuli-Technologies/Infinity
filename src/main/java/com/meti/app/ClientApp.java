package com.meti.app;

import com.meti.lib.collect.SetBasedState;
import com.meti.lib.collect.State;
import com.meti.lib.fx.InjectorLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/1/2019
 */
public class ClientApp extends Application {
    private final State state = new SetBasedState();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent = InjectorLoader.load(List.of(state), getClientDisplayURL());
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }

    private URL getClientDisplayURL() {
        return getClass().getResource("/com/meti/app/client/ClientDisplay.fxml");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
