package com.meti.lib.fx;

import com.meti.lib.State;
import javafx.fxml.FXMLLoader;

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
}
