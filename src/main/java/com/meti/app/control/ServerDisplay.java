package com.meti.app.control;

import com.meti.lib.State;
import com.meti.lib.io.Server;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Future;
import java.util.logging.Level;

public class ServerDisplay extends InfinityServerController implements Initializable {
    @FXML
    private Text portText;

    @FXML
    private ListView<String> clientListView;

    public ServerDisplay(State state) {
        super(state);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        portText.setText(String.valueOf(server.supplier.serverSocket.getLocalPort()));
        server.onAccept = socketSource -> clientListView.getItems().add(socketSource.socket.getInetAddress().toString());
        Future<Server> future = serviceManager.service.submit(server.getListener());
        new InfinityFXChecker(future, state, Level.SEVERE).start();
    }
}
