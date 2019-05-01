package com.meti.app.control.server;

import com.meti.app.control.util.InfinityFXChecker;
import com.meti.app.io.InfinityServer;
import com.meti.lib.util.collect.State;
import com.meti.lib.io.server.Server;
import com.meti.lib.io.source.SocketSource;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Future;
import java.util.function.Consumer;
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
        Future<Server> future = setPortText(server).listen(setPortText(server).server);
        new InfinityFXChecker(future, state, Level.SEVERE).start();
    }

    private Future<Server> listen(InfinityServer server) {
        return serviceManager.service.submit(server.setOnAccept(new AcceptConsumer()).getListener());
    }

    private ServerDisplay setPortText(InfinityServer server) {
        portText.setText(String.valueOf(server.supplier.serverSocket.getLocalPort()));
        return this;
    }

    private class AcceptConsumer implements Consumer<SocketSource> {
        @Override
        public void accept(SocketSource socketSource) {
            clientListView.getItems().add(socketSource.socket.getInetAddress().toString());
        }
    }
}
