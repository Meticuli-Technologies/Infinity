package com.meti.app.control;

import com.meti.lib.State;
import javafx.fxml.FXML;

import java.io.IOException;

public class Menu extends InfinityController {
    public Menu(State state) {
        super(state);
    }

    @FXML
    public void connect() {
        try {
            onto(getClass().getResource("/com/meti/app/control/ClientMenu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void host() {
        try {
            onto(getClass().getResource("/com/meti/app/control/ServerMenu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
