package com.meti;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class Menu extends Controller {
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

            ExecutorService service = Executors.newCachedThreadPool();
            state.add(service);

            Server server = new Server(serverSocket, service);
            state.add(server);
            service.submit(server);

            Socket socket = new Socket(InetAddress.getByName("localhost"), port);
            Querier querier = new Querier(socket);
            state.add(querier);
            service.submit(querier);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
