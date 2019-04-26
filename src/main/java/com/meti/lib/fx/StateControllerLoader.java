package com.meti.lib.fx;

import com.meti.lib.State;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;

public class StateControllerLoader extends FXMLLoader {

    private StateControllerLoader(URL url, State state) {
        super(url);
        setControllerFactory(new StateControllerFactory(state));
    }

    public static <T> T loadRoot(URL url, State state) throws IOException {
        return new StateControllerLoader(url, state).load();
    }

    public static <T> FXMLBundle<T> loadFXMLBundleFrom(URL url, State state) throws IOException {
        return new StateControllerLoader(url, state).loadFXMLBundleFrom();
    }

    public <T> FXMLBundle<T> loadFXMLBundleFrom() throws IOException {
        return new FXMLBundle<>(load(), getController());
    }
}
