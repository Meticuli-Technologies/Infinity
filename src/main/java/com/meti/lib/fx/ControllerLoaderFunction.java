package com.meti.lib.fx;

import com.meti.lib.collect.State;
import com.meti.lib.collect.tryable.TryableFunction;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class ControllerLoaderFunction<T> implements TryableFunction<InputStream, FXMLBundle<T>> {
    private ControllerLoader loader;

    public ControllerLoaderFunction(State state, Stage stage) {
        this.loader = new ControllerLoader(state, stage);
    }

    @Override
    public FXMLBundle<T> apply(InputStream inputStream) throws IOException {
        return loader.getBundle(inputStream);
    }
}
