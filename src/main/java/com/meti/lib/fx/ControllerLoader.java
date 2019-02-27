package com.meti.lib.fx;

import com.meti.lib.State;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;

public class ControllerLoader extends FXMLLoader {
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

    public static <T> T load(URL url, State state) throws IOException {
        return new ControllerLoader(url, state).load();
    }
}
