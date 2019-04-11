package com.meti;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class Controller {
    final State state;

    public Controller(State state) {
        this.state = state;
    }

    public void onto(URL url) throws IOException {
        Stage stage = state.byClassToSingle(Stage.class);
        onto(url, stage);
    }

    private void onto(URL url, Stage stage) throws IOException {
        stage.setScene(new Scene(ControllerLoader.load(url, state)));
        if (!stage.isShowing()) {
            stage.show();
        }
    }

    public void onto(URL url, int index) throws IOException {
        List<Stage> stages = state.byClass(Stage.class)
                .collect(Collectors.toList());

        Stage toSet;
        if (stages.size() > index) {
            toSet = stages.get(index);
        } else {
            toSet = new Stage();
        }

        onto(url, toSet);
    }
}
