package com.meti.app.control;

import com.meti.app.client.InfinityClient;
import com.meti.app.core.state.StateImpl;
import com.meti.app.core.state.Toolkit;
import com.meti.app.server.InfinityServer;
import com.meti.lib.javafx.StageManagerImpl;
import com.meti.lib.net.Listener;
import com.meti.lib.source.ObjectSource;
import com.meti.lib.source.PortSourceSupplier;
import com.meti.lib.source.SocketSupplier;
import com.meti.lib.source.socket.SocketSource;
import javafx.fxml.FXML;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;

import static com.meti.lib.javafx.Injector.readAsScene;
import static com.meti.lib.source.url.URLSource.fromResource;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public class Menu {
    private final Toolkit toolkit;

    public Menu(Toolkit toolkit) {
        this.toolkit = toolkit;
    }

    @FXML
    public void local() {
        try {
            StateImpl state = toolkit.getState();
            int localPort = constructServer(state, new SocketSupplier(0));
            constructClient(state, localPort);
            Scene serverDisplayScene = readAsScene(fromResource("/com/meti/app/control/ServerDisplay.fxml"), toolkit);
            loadStageManager(serverDisplayScene, toolkit.getStageManager());
        } catch (IOException e) {
            toolkit.getLogger().log(Level.WARNING, "Failed to build IO", e);
        }
    }

    private int constructServer(StateImpl state, PortSourceSupplier supplier) {
        Listener server = new InfinityServer(supplier);
        server.listen();
        state.add(server);
        return supplier.getPort();
    }

    private void loadStageManager(Scene serverDisplayScene, StageManagerImpl stageManager) {
        stageManager.ontoPrimaryStage(serverDisplayScene);
    }

    private void constructClient(StateImpl state, int localPort) throws IOException {
        Listener client = new InfinityClient(new ObjectSource(new SocketSource(new Socket(InetAddress.getByName("localhost"), localPort))));
        client.listen();
        state.add(client);
    }
}
