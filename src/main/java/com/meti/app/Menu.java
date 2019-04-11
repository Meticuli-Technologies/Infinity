package com.meti.app;

import com.meti.app.control.InfinityController;
import com.meti.lib.net.Client;
import com.meti.lib.net.Querier;
import com.meti.lib.net.InfinityServer;
import com.meti.lib.util.State;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class Menu extends InfinityController {
    @FXML
    private TextField portField;

    public Menu(State state) {
        super(state);
    }

    @FXML
    public void exit(){
        Platform.exit();
    }

    @FXML
    public void next(){
        try {
            int port = Integer.parseInt(portField.getText());
            ServerSocket serverSocket = new ServerSocket(port);

            InfinityServer server = new InfinityServer(serverSocket, service);
            state.add(server);
            service.submit(server);

            onto(getClass().getResource("/com/meti/ServerDisplay.fxml"));

            Socket socket = new Socket(InetAddress.getByName("localhost"), port);
            Client client = new Client(socket);
            state.add(client);

            Querier querier = new Querier(client);
            state.add(querier);
            service.submit(querier);

            onto(getClass().getResource("/com/meti/ClientDisplay.fxml"), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
