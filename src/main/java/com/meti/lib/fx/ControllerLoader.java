package com.meti.lib.fx;

import com.meti.lib.util.collect.State;
import com.meti.lib.fx.fxml.FXMLBundle;
import com.meti.lib.fx.state.StateControllerFactory;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;

public class ControllerLoader extends FXMLLoader {

    private ControllerLoader(URL url, State state) {
        super(url);
        setControllerFactory(new StateControllerFactory(state));
    }

    public static <T> T loadRoot(URL url, State state) throws IOException {
        return new ControllerLoader(url, state).load();
    }

    public static <T> FXMLBundle<T> loadFXMLBundleFrom(URL url, State state) throws IOException {
        return new ControllerLoader(url, state).loadFXMLBundleFrom();
    }

    private <T> FXMLBundle<T> loadFXMLBundleFrom() throws IOException {
        return new FXMLBundle<>(load(), getController());
    }
}
