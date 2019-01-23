package com.meti.app;

import com.meti.lib.State;
import com.meti.lib.fx.ControllerLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private final State state = new State();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        state.add(primaryStage);

        primaryStage.setScene(new Scene(ControllerLoader.load(getClass().getResource("/com/meti/app/Display.fxml"), state)));
        primaryStage.show();
    }
}
