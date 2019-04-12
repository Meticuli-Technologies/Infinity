package com.meti.app.control;

import com.meti.lib.net.InfinityServer;
import com.meti.lib.util.State;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class Host extends InfinityController {
    @FXML
    private TextField portField;

    public Host(State state) {
        super(state);
    }

    @FXML
    public void back() {
        try {
            onto(getClass().getResource("/com/meti/Menu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void next() {
        try {
            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(portField.getText()));
            InfinityServer server = new InfinityServer(serverSocket, service);
            state.add(server);
            service.submit(server);

            onto(getClass().getResource("/com/meti/ServerDisplay.fxml"), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
