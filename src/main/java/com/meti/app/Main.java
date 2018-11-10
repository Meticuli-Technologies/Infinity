package com.meti.app;

import com.meti.lib.fx.ControllerLoader;
import com.meti.lib.fx.ControllerState;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ControllerState state = new ControllerState(primaryStage, LoggerFactory.getLogger(Main.class));

        primaryStage.setScene(new Scene(ControllerLoader.load(getClass().getResource("/com/meti/app/Menu.fxml"), state)));
        primaryStage.show();
    }

}
