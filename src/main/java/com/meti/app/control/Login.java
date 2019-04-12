package com.meti.app.control;

import com.meti.lib.util.State;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class Login extends InfinityClientController {
    @FXML
    private TextField usernameField;

    public Login(State state) {
        super(state);
    }

    @FXML
    public void cancel() {
        try {
            client.close();

            onto(getClass().getResource("/com/meti/Menu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void next() {
        try {
            state.add(querier.query(new LoginRequest(usernameField.getText())).get());
            onto(getClass().getResource("/com/meti/ClientDisplay.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
