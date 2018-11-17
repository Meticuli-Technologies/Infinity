package com.meti.lib.fx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class ControllerLoader extends FXMLLoader {
    private final InfinityState state;

    public ControllerLoader(URL location, InfinityState state) {
        super(location);
        this.state = state;
    }

    public static Parent load(URL url, InfinityState state) throws IOException {
        return new ControllerLoader(url, state).load();
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
