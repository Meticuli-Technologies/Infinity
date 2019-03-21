package com.meti.app.control;

import com.meti.lib.State;
import com.meti.lib.net.Client;
import com.meti.lib.respond.OKResponse;
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
    public void back(){
        try {
            Client client = getClientOrThrow();
            client.close();

            state.remove(client);

            onto(getClass().getResource("/com/meti/app/control/ClientMenu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void next(){
        try {
            Client client = getClientOrThrow();
            OKResponse response = client.queryObject(new com.meti.app.feature.Login(usernameField.getText()), OKResponse.class);
            assert response != null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
