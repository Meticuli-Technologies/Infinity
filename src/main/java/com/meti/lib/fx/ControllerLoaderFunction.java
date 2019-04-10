package com.meti.lib.fx;

import com.meti.lib.collect.tryable.TryableFunction;

import java.io.IOException;
import java.io.InputStream;

public class ControllerLoaderFunction<T> implements TryableFunction<InputStream, FXMLBundle<T>> {
    private Controller controller;

    public ControllerLoaderFunction(Controller controller) {
        this.controller = controller;
    }

    @Override
    public FXMLBundle<T> apply(InputStream inputStream) throws IOException {
        return new ControllerLoader(controller.state, controller.stage).getBundle(inputStream);
    }
}
