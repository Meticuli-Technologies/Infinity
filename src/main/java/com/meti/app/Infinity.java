package com.meti.app;

import com.meti.lib.State;
import com.meti.lib.fx.ControllerLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Infinity {
    private final State state = new State();

    public void start(Stage primaryStage) throws IOException {
        state.add(primaryStage);

        primaryStage.setScene(new Scene(ControllerLoader.load(checkDisplayURL(), state)));
        primaryStage.setTitle("Infinity");
        primaryStage.show();
    }

    public URL checkDisplayURL() {
        return getClass().getResource("/com/meti/app/Display.fxml");
    }
}
