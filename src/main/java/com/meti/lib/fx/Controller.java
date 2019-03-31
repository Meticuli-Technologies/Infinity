package com.meti.lib.fx;

import com.meti.lib.collection.State;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/31/2019
 */
public class Controller {
    private final State state;

    public Controller(State state) {
        this.state = state;
    }

    public void onto(URL url) throws IOException {
        onto((Parent) ControllerLoader.load(url, state));
    }

    public void onto(Parent parent) {
        Optional<Stage> any = state.byClass(Stage.class).findAny();
        Stage toSet;
        if (any.isPresent()) {
            toSet = any.get();
        } else {
            toSet = new Stage();
            state.add(toSet);
        }

        toSet.setScene(new Scene(parent));
        if (!toSet.isShowing()) {
            toSet.show();
        }
    }
}
