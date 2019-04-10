package com.meti.lib.fx;

import com.meti.lib.collect.State;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/7/2019
 */
public class Controller {
    protected final State state;
    final Stage stage;

    public Controller(State state, Stage stage) {
        this.stage = stage;
        this.state = state;
    }

    public <T> T onto(InputStream inputStream, Function<InputStream, FXMLBundle<T>> bundleFunction) {
        FXMLBundle<T> bundle = bundleFunction.apply(inputStream);

        stage.setScene(new Scene(bundle.parent));
        if (!stage.isShowing()) {
            stage.show();
        }

        return bundle.controller;
    }

    public class ControllerLoaderFunction<T> implements Function<InputStream, FXMLBundle<T>> {
        @Override
        public FXMLBundle<T> apply(InputStream inputStream) {
            try {
                return new ControllerLoader(state, stage)
                        .getBundle(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
