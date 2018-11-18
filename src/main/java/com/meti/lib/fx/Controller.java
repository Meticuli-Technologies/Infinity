package com.meti.lib.fx;

import com.meti.lib.State;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class Controller {
    protected State state;


    public Stage onto(URL url) throws IOException {
        return onto(0, url);
    }

    public Stage onto(int stageIndex, URL url) throws IOException {
        return onto(url, url.getPath(), state.ofType(Stage.class).get(stageIndex));
    }

    public Stage onto(URL url, String title, Stage stage) throws IOException {
        stage.setScene(ControllerLoader.loadToScene(url, state));

        if (title != null) {
            stage.setTitle(title);
        }

        if (!stage.isShowing()) {
            stage.show();
        }

        return stage;
    }

    public Stage onto(URL url, String title) throws IOException {
        return onto(url, title, 0);
    }

    public Stage onto(URL url, String title, int stageIndex) throws IOException {
        return onto(url, title, state.ofType(Stage.class).get(stageIndex));
    }

    public void setState(State state) {
        this.state = state;
    }
}
