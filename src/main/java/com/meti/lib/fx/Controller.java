package com.meti.lib.fx;

import com.meti.lib.collection.State;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/31/2019
 */
public class Controller {
    protected final State state;

    public Controller(State state) {
        this.state = state;
    }

    public void onto(URL url) throws IOException {
        Optional<Stage> any = state.byClass(Stage.class).findAny();
        Stage toSet;
        if (any.isPresent()) {
            toSet = any.get();
        } else {
            toSet = new Stage();
            state.add(toSet);
        }

        onto(url, toSet);
    }

    public void onto(URL url, Stage toSet) throws IOException {
        Parent parent = ControllerLoader.load(url, state);
        toSet.setScene(new Scene(parent));
        if (!toSet.isShowing()) {
            toSet.show();
        }
    }

    public void onto(URL url, int index) throws IOException {
        List<Stage> stageList = state.byClass(Stage.class).collect(Collectors.toList());
        Stage toSet;
        if (stageList.size() > index) {
            toSet = stageList.get(index);
        } else {
            toSet = new Stage();
        }

        onto(url, toSet);
    }
}
