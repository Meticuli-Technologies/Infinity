package com.meti.lib.fx;

import com.meti.lib.collect.State;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/7/2019
 */
public class ControllerLoader extends FXMLLoader {
    final State state;

    public ControllerLoader(State state) {
        this.state = state;
        setControllerFactory(new ControllerCallback(state));
    }

    public static <T> T load(InputStream inputStream, State state) throws IOException {
        return new ControllerLoader(state).load(inputStream);
    }

    public <T> FXMLBundle<T> getBundle(InputStream inputStream) throws IOException {
        return new FXMLBundle<>(load(inputStream), getController());
    }
}
