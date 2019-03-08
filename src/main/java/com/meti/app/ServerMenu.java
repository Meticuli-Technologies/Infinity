package com.meti.app;

import com.meti.lib.State;
import com.meti.lib.fx.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.ServerSocket;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/24/2019
 */
public class ServerMenu extends Controller {
    @FXML
    private TextField portField;

    public ServerMenu(State state) {
        super(state);
    }

    @FXML
    public void back(){
        try {
            ontoURL(getClass().getResource("/com/meti/app/Menu.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void next(){
        try {
            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(portField.getText()));
            ServerDisplay menu = ontoURL(getClass().getResource("/com/meti/app/ServerDisplay.fxml"));
            menu.load(serverSocket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
