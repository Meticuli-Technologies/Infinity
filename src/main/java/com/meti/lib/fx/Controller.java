package com.meti.lib.fx;

import com.meti.lib.collect.State;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

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

    public <T> T onto(InputStream inputStream) throws IOException {
        FXMLBundle<T> bundle = new ControllerLoader(state, stage)
                .getBundle(inputStream);

        stage.setScene(new Scene(bundle.parent));
        if (stage.isShowing()) {
            stage.show();
        }

        return bundle.controller;
    }
}
