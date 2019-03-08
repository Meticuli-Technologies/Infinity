package com.meti.lib.fx;

import com.meti.lib.State;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Controller {
    protected final State state;

    public Controller(State state) {
        this.state = state;
    }

    public <T> T ontoURL(URL url) throws Exception {
        ControllerLoader loader = new ControllerLoader(url, state);
        ontoParent(loader.load());
        return loader.getController();
    }

    public Stage ontoParent(Parent parent) throws Exception {
        return ontoParent(parent, 0);
    }

    public Stage ontoParent(Parent parent, int index) throws Exception {
        Stage stage = state.byClass(Stage.class).get(index);
        stage.setScene(new Scene(parent));
        return stage;
    }
}
