package com.meti.lib.fx;

import com.meti.lib.collection.State;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/31/2019
 */
public class ControllerLoader extends FXMLLoader {
    private final State state;

    public ControllerLoader(State state) {
        this.state = state;

        setControllerFactory(param -> {
            if (Controller.class.isAssignableFrom(param)) {
                try {
                    return param.getConstructor(State.class).newInstance(state);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                throw new IllegalArgumentException(param + " is not assignable to " + Controller.class);
            }
        });
    }

    public static <T> T load(URL url, State state) throws IOException {
        return new ControllerLoader(state).load(url.openStream());
    }
}
