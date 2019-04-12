package com.meti.app;

import com.meti.app.control.InfinityController;
import com.meti.lib.net.Client;
import com.meti.lib.net.InfinityServer;
import com.meti.lib.net.Querier;
import com.meti.lib.util.State;
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
public class Local extends InfinityController {
    @FXML
    private TextField portField;

    public Local(State state) {
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
    public void next(){
        try {
            int port = Integer.parseInt(portField.getText());
            createServer(port);
            createClient(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createServer(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        InfinityServer server = new InfinityServer(serverSocket, service);
        state.add(server);
        service.submit(server);

        onto(getClass().getResource("/com/meti/ServerDisplay.fxml"), 1);
    }

    public void createClient(int port) throws IOException {
        Socket socket = new Socket(InetAddress.getByName("localhost"), port);
        Client client = new Client(socket);
        state.add(client);

        Querier querier = new Querier(client);
        state.add(querier);
        service.submit(querier);

        onto(getClass().getResource("/com/meti/Login.fxml"));
    }
}
