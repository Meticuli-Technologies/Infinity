package com.meti.app.server;

import com.meti.app.Controls;
import com.meti.app.InfinityController;
import com.meti.app.asset.AssetRequest;
import com.meti.app.asset.InlineAssetResponse;
import com.meti.app.client.chat.ChatMessageHandler;
import com.meti.app.server.asset.AssetPropertiesRequestHandler;
import com.meti.lib.asset.manage.AssetManager;
import com.meti.lib.net.TypeHandler;
import com.meti.lib.net.client.Client;
import com.meti.lib.net.server.Server;
import com.meti.lib.net.server.ServerSocketServer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.io.Serializable;
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
        server.listen();
        state.add(server);
        loadServerOntoGUI(server);
    }

    private void loadServerOntoGUI(Server server) {
        server.setOnConnect(client -> Platform.runLater(() -> clientListView.getItems().add(client.getName())));
        loadHandlers(server);
        loadPortText(server.getPort());
    }

    private void loadHandlers(Server server) {
        server.getResponseHandlers().add(new ChatMessageHandler(server));
        server.getResponseHandlers().add(new AssetPropertiesRequestHandler(toolkit.getAssetManager()));
        server.getResponseHandlers().add(new AssetRequestHandler());
    }

    private void loadPortText(int port) {
        portText.setText(String.valueOf(port));
    }

    private class AssetRequestHandler extends TypeHandler<AssetRequest> {
        public AssetRequestHandler() {
            super(AssetRequest.class);
        }

        @Override
        public Optional<Serializable> handleGeneric(AssetRequest response, Client client) {
            AssetManager assetManager = toolkit.getAssetManager();
            String assetName = response.getName();
            return lookupAsset(assetManager, assetName);
        }

        private Optional<Serializable> lookupAsset(AssetManager assetManager, String assetName) {
            return Optional.of(new InlineAssetResponse(assetManager.getAssetByName(assetName)));
        }
    }
}
