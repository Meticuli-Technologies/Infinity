package com.meti.lib.fx;

import com.meti.lib.State;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class ControllerLoader extends FXMLLoader {
    private final State state;

    private ControllerLoader(URL location, State state) {
        super(location);
        this.state = state;
    }

    public static Scene loadToScene(URL url, State state) throws IOException {
        return new Scene(loadToParent(url, state));
    }

    private static Parent loadToParent(URL url, State state) throws IOException {
        return new ControllerLoader(url, state).load();
    }

    public static Scene loadToScene(URL url, State state, Scene scene) throws IOException {
        scene.setRoot(loadToParent(url, state));
        return scene;
    }

    @Override
    public <T> T load() throws IOException {
        T parent = super.load();

        Object controllerToken = getController();
        if (controllerToken instanceof Controller) {
            Controller controller = (Controller) controllerToken;
            controller.setState(state);

            state.addObject(controller);
        }

        if(controllerToken instanceof PostInitializable){
            ((PostInitializable) controllerToken).postInitialize();
        }

        return parent;
    }
}
