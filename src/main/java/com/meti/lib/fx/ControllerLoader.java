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
    }

    public static <T> T load(URL url, State state) throws IOException {
        return new ControllerLoader(url, state).load();
    }

    @Override
    public <T> T load() throws IOException {
        T load = super.load();

        Object controllerToken = getController();
        if (controllerToken instanceof Controller) {
            //state should be null
            Controller controller = (Controller) controllerToken;
            controller.setState(state);
            controller.complete();
        }

        return load;
    }
}
