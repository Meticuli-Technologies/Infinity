package com.meti.lib.fx;

import com.meti.lib.State;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;

public class StateControllerLoader extends FXMLLoader {

    private StateControllerLoader(URL url, State state) {
        super(url);
        State state1 = state;

        setControllerFactory(new StateControllerFactory(state));
    }

    public static <T> T load(URL url, State state) throws IOException {
        return new StateControllerLoader(url, state).load();
    }
}
