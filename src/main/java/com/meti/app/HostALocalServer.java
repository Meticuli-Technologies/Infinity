package com.meti.app;

import com.meti.lib.fx.Controller;
import com.meti.lib.fx.ControllerLoader;
import com.meti.lib.net.client.Client;
import com.meti.lib.net.connect.SocketConnection;
import com.meti.lib.net.server.Server;
import com.meti.lib.net.server.ServerClientConsumer;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class HostALocalServer extends Controller {
    private static final int DEFAULT_PORT = 80;

    @FXML
    private TextField portField;

    @FXML
    public void back(){
        try {
            state.getPrimaryStage().setScene(ControllerLoader.loadToScene(getClass().getResource("/com/meti/app/Menu.fxml"), state));
        } catch (IOException e) {
            state.getLogger().error("", e);
        }
    }

    @FXML
    public void next(){
        try {
            int port = getPort();

            Server server = createServerDisplay(port, state.getService());
            state.getLogger().info("Started server on port " + server.serverSocket.getLocalPort());

            Client<SocketConnection> client = createClientDisplay(port);
            state.getLogger().info("Connected to local server at address " + client.connection.socket.getInetAddress().toString());
        } catch (IOException e) {
            state.getLogger().error("", e);
        }
    }

    private int getPort() {
        int port;
        try {
            port = Integer.parseInt(portField.getText());
        } catch (NumberFormatException e) {
            port = DEFAULT_PORT;
        }
        return port;
    }

    private Server createServerDisplay(int port, ExecutorService service) throws IOException {
        Server server = createServer(port, service);
        state.addObject(server);

        onto(getClass().getResource("/com/meti/app/ServerDisplay.fxml"), "Infinity Server");
        return server;
    }

    private Client<SocketConnection> createClientDisplay(int port) throws IOException {
        Client<SocketConnection> client = createClient(port);
        Stage clientStage = createClientStage();
        state.addObject(clientStage);
        return client;
    }

    private Server createServer(int port, ExecutorService service) throws IOException {
        Server server = new Server(port, new ServerClientConsumer());
        server.start(service);
        return server;
    }

    private Client<SocketConnection> createClient(int port) throws IOException {
        Client<SocketConnection> client = new Client<>(new SocketConnection(new Socket(InetAddress.getByName("localhost"), port)));
        client.listen(state.getService());
        state.addObject(client);
        return client;
    }

    private Stage createClientStage() throws IOException {
        Stage primaryStage = state.getPrimaryStage();
        Stage clientStage = createClientStage(primaryStage.getX(), primaryStage.getY());

        onto(getClass().getResource("/com/meti/app/ClientDisplay.fxml"), "Infinity", clientStage);
        return clientStage;
    }

    private Stage createClientStage(double x, double y) {
        Stage clientStage = new Stage();
        clientStage.setX(x);
        clientStage.setY(y);
        return clientStage;
    }
}
