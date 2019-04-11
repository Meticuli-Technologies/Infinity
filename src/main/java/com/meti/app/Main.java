package com.meti.app;

import com.meti.lib.util.State;
import com.meti.lib.control.ControllerLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        State state = new State();
        state.add(primaryStage);

        primaryStage.setScene(new Scene(ControllerLoader.load(getClass().getResource("/com/meti/Menu.fxml"), state)));
        primaryStage.show();
    }

    public static void main(String[] args) {
     launch(args);
    }

}
