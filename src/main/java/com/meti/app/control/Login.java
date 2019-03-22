package com.meti.app.control;

import com.meti.app.User;
import com.meti.lib.State;
import com.meti.lib.net.Client;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/21/2019
 */
public class Login extends InfinityController {
    @FXML
    private TextField usernameField;

    public Login(State state) {
        super(state);
    }

    @FXML
    public void back() {
        try {
            Client client = getClient();
            client.close();

            state.remove(client);

            onto(getClass().getResource("/com/meti/app/control/ClientMenu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void next() {
        try {
            Client client = getClient();
            User user = client.queryObject(new com.meti.app.feature.Login(usernameField.getText()), User.class);
            state.add(user);

            onto(getClass().getResource("/com/meti/app/control/ClientDisplay.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
