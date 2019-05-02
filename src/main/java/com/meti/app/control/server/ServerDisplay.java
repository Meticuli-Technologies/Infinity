package com.meti.app.control.server;

import com.meti.app.io.InfinityServer;
import com.meti.lib.io.source.SocketSource;
import com.meti.lib.util.collect.State;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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
        setPortText(server).setOnAccept(new AcceptConsumer());
        server.sources.forEach(this::addClient);
    }

    private InfinityServer setPortText(InfinityServer server) {
        portText.setText(String.valueOf(server.supplier.serverSocket.getLocalPort()));
        return server;
    }

    private class AcceptConsumer implements Consumer<SocketSource> {
        @Override
        public void accept(SocketSource socketSource) {
            addClient(socketSource);
        }
    }

    private void addClient(SocketSource socketSource) {
        clientListView.getItems().add(socketSource.socket.getInetAddress().toString());
    }
}
