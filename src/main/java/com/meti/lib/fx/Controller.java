package com.meti.lib.fx;

import com.meti.lib.State;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller {
    protected final State state;

    public Controller(State state) {
        this.state = state;
    }

    public Stage onto(Parent parent) throws Exception {
        return onto(parent, 0);
    }

    public Stage onto(Parent parent, int index) throws Exception {
        Stage stage = state.byClass(Stage.class).get(index);
        stage.setScene(new Scene(parent));
        return stage;
    }
}
