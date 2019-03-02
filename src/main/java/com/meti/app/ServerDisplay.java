package com.meti.app;

import com.meti.lib.net.Server;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.net.ServerSocket;
import java.util.Optional;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/2/2019
 */
public class ServerDisplay {
    @FXML
    private ListView<String> clientListView;

    private Server server;

    public void load(ServerSocket serverSocket) {
        this.server = new Server(serverSocket);
    }

    private Optional<Server> getServer(){
        return Optional.ofNullable(server);
    }
}
