package com.meti.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

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
        ControllerState state = ControllerState.of(primaryStage, LoggerFactory.getLogger(Main.class));

        primaryStage.setScene(new Scene(load(getClass().getResource("/com/meti/app/Menu.fxml"), state)));
        primaryStage.show();
    }

    public static Parent load(URL url, ControllerState state) throws IOException {
        FXMLLoader loader = new FXMLLoader(url);
        Parent load = loader.load();

        Object controller = loader.getController();
        if(controller instanceof Controller){
            ((Controller) controller).setState(state);
        }

        return load;
    }
}
