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
    public static final Set<Finalizable> finalizables = new HashSet<>();
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

        Object controller = getController();
        if (controller instanceof Controller) {
            ((Controller) controller).setState(state);
        }

        if(controller instanceof PostInitializable){
            ((PostInitializable) controller).postInitialize();
        }

        if(controller instanceof Finalizable){
            finalizables.add((Finalizable) controller);
        }

        return parent;
    }
}
