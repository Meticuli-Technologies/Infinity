package com.meti.app;

import com.meti.lib.fx.Controller;
import com.meti.lib.fx.ControllerLoader;
import com.meti.lib.net.client.Client;
import com.meti.lib.net.server.Server;
import com.meti.lib.net.connect.SocketConnection;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class HostALocalServer extends Controller {
    public static final int DEFAULT_PORT = 80;
    @FXML
    private TextField portField;

    @FXML
    public void back(){
        try {
            state.firstOfType(Stage.class)
                    .orElse(new Stage())
                    .setScene(new Scene(ControllerLoader.load(getClass().getResource("/com/meti/app/Menu.fxml"), state)));
        } catch (IOException e) {
            getLogger().error("", e);
        }
    }

    @FXML
    public void next(){
        try {
            int port;
            try {
                port = Integer.parseInt(portField.getText());
            } catch (NumberFormatException e) {
                port = DEFAULT_PORT;
            }

            Server server = new Server(port, new InfinityConsumer());
            server.start();

            state.addObject(server);
            state.firstOfType(Stage.class)
                    .orElse(new Stage())
                    .setScene(new Scene(ControllerLoader.load(getClass().getResource("/com/meti/app/ServerDisplay.fxml"), state)));

            Client<SocketConnection> client = new Client<>(new SocketConnection(new Socket(InetAddress.getByName("localhost"), port)));
            state.addObject(client);

            Stage clientStage = new Stage();
            clientStage.setScene(new Scene(ControllerLoader.load(getClass().getResource("/com/meti/app/ClientDisplay.fxml"), state)));
            clientStage.show();

            state.addObject(clientStage);
        } catch (IOException e) {
            getLogger().error("", e);
        }
    }
}
