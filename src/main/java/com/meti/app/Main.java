package com.meti.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/24/2019
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            URL resource = getClass().getResource("/com/meti/app/Menu.fxml");
            FXMLLoader loader = new FXMLLoader(resource);
            primaryStage.setScene(new Scene(loader.load()));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ControllerLoader extends FXMLLoader {
        private final State state;

        public ControllerLoader(URL location, State state) {
            super(location);
            this.state = state;

            setControllerFactory(param -> {
                if (!Controller.class.isAssignableFrom(param)) {
                    throw new IllegalArgumentException(param.getName() + " is not assignable to " + Controller.class);
                }
                try {
                    return param.getConstructor(State.class).newInstance(state);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Failed to instantiate " + param.getName());
                }
            });
        }

        private class Controller {
            protected final State state;

            public Controller(State state) {
                this.state = state;
            }
        }
    }

    private class State extends HashSet<Object> {
    }
}
