package com.meti.lib.fx;

import com.meti.lib.State;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Controller {
    protected final State state;
    protected Stage stage;

    public Controller(State state) {
        this.state = state;
    }

    public void onto(URL url) throws IOException {
        onto(ControllerLoader.<Parent>load(url));
    }

    public void onto(Parent parent) {
        onto(new Scene(parent));
    }

    public void onto(Scene scene) {
        stage.setScene(scene);

        if (!stage.isShowing()) {
            stage.show();
        }
    }
}
