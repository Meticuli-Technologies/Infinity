package com.meti.lib.fx;

import com.meti.lib.State;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ControllerLoader extends FXMLLoader {
    public ControllerLoader(URL location, State state) {
        super(location);

        setControllerFactory(param -> {
            if (!Controller.class.isAssignableFrom(param)) {
                throw new IllegalArgumentException(param + " must be a subclass of " + Controller.class);
            }

            try {
                return param.getDeclaredConstructor(State.class).newInstance(state);
            } catch (Exception e) {
                //TODO: handle exception
                e.printStackTrace();
                return null;
            }
        });
    }

    public static <T extends Controller> T onto(URL url, State state, Stage stage) throws IOException {
        ControllerLoader loader = new ControllerLoader(url, state);
        stage.setScene(new Scene(loader.load()));

        T controller = loader.getController();
        controller.stage = stage;

        if(!stage.isShowing()){
            stage.show();
        }

        return controller;
    }

    public static <T> T load(URL location, State state) throws IOException {
        return new ControllerLoader(location, state).load();
    }
}
