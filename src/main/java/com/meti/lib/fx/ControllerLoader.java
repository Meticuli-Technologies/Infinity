package com.meti.lib.fx;

import com.meti.lib.util.Finalizable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class ControllerLoader extends FXMLLoader {
    private final ControllerState state;

    public ControllerLoader(URL location, ControllerState state) {
        super(location);
        this.state = state;
    }

    public static Parent load(URL url, ControllerState state) throws IOException {
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
