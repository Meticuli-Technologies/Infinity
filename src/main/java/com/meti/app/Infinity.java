package com.meti.app;

import com.meti.lib.State;
import com.meti.lib.fx.ControllerLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Infinity {
    private final State state = new State();

    public void start(Stage primaryStage) throws IOException {
        state.add(primaryStage);

        primaryStage.setScene(new Scene(ControllerLoader.load(getClass().getResource("/com/meti/app/Menu.fxml"))));
        primaryStage.setTitle("Infinity");
        primaryStage.show();
    }
}
