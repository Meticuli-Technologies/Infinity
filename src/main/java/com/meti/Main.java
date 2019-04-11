package com.meti;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        State state = new State();
        primaryStage.setScene(new Scene(ControllerLoader.load(getClass().getResource("/com/meti/Menu.fxml"), state)));
        primaryStage.show();
    }

    public static void main(String[] args) {
     launch(args);
    }

    private static class ControllerLoader extends FXMLLoader {
        private final State state;

        private ControllerLoader(URL url, State state) {
            super(url);
            this.state = state;

            setControllerFactory(param -> {
                if (Controller.class.isAssignableFrom(param)) {
                    try {
                        return param.getDeclaredConstructor(State.class).newInstance(state);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    throw new IllegalArgumentException(param + " is not assignable to " + Controller.class);
                }
            });
        }

        public static <T> T load(URL url, State state) throws IOException {
            return new ControllerLoader(url, state).load();
        }
    }
}
