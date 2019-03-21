package com.meti.lib.fx;

import com.meti.lib.State;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Controller {
    protected final State state;
    protected Stage stage;

    public Controller(State state) {
        this.state = state;
    }

    public <T extends Controller> T onto(URL url) throws IOException {
        return ControllerLoader.onto(url, state, stage);
    }
}
