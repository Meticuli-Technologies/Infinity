package com.meti.lib.fx;

import com.meti.lib.State;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class Controller {
    /*
    this variable is effectively final

    we want this variable to be unmodifiable, however still injectable
    from ControllerLoader

    as a result, we can't initialize this field in the constructor
     */
    private State state;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        if (this.state != null) {
            throw new IllegalStateException("State has already been set!");
        } else {
            this.state = state;
        }
    }

    public void loadNew(URL url) throws IOException {
        state.add(new Stage());

        load(url, stageSize() - 1);
    }

    public void load(URL url, int index) throws IOException {
        if (stageSize() < index + 1) {
            throw new IllegalArgumentException(index + " is less than " + stageSize());
        }

        Parent parent = ControllerLoader.load(url);
        List<Stage> stages = state.storedList(Stage.class);
        stages.get(index).setScene(new Scene(parent));
    }

    private int stageSize() {
        return state.storedList(Stage.class).size();
    }

    public void load(URL url) throws IOException {
        load(url, 0);
    }

    /**
     * <p>
     * This method is intentionally left empty, and exists to be overridden.
     * This method is called after {@link ControllerLoader#load}
     * </p>
     */
    public void complete() {
    }
}
