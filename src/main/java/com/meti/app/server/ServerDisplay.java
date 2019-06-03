package com.meti.app.server;

import com.meti.app.Controls;
import com.meti.app.StringResponseHandler;
import com.meti.app.client.InfinityController;
import com.meti.lib.net.server.Server;
import com.meti.lib.net.server.ServerSocketServer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/3/2019
 */
public class ServerDisplay extends InfinityController implements Initializable {
    @FXML
    private ListView<String> clientListView;

    @FXML
    private Text portText;

    public ServerDisplay(Controls controls) {
        super(controls);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Optional<ServerBootstrap> bootstrap = state.singleByClass(ServerBootstrap.class);
        if (bootstrap.isPresent()) {
            loadServer(bootstrap.get());
        } else {
            //TODO: fill else
        }
    }

    private void loadServer(ServerBootstrap serverBootstrap) {
        try {
            tryLoadServer(serverBootstrap.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void tryLoadServer(int port) throws IOException {
        Server server = new ServerSocketServer(port, Collections.emptySet());
        portText.setText(String.valueOf(server.getPort()));
        server.listen();
        server.getResponseHandlers().add(new StringResponseHandler(server));
    }
}
