package com.meti.app;

import com.meti.lib.State;
import com.meti.lib.fx.ControllerLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/24/2019
 */
public class Main extends Application {
    private final State state = new State();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            state.add(primaryStage);

            URL resource = getClass().getResource("/com/meti/app/Menu.fxml");
            primaryStage.setScene(new Scene(ControllerLoader.load(resource, state)));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
