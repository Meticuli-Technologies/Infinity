package com.meti.app.control;

import com.meti.app.core.state.StateImpl;
import com.meti.app.core.state.Toolkit;
import com.meti.app.server.InfinityServer;
import com.meti.lib.javafx.StageManagerImpl;
import com.meti.lib.net.Listener;
import com.meti.lib.source.PortSourceSupplier;
import com.meti.lib.source.SocketSupplier;
import com.meti.lib.source.socket.SocketSource;
import javafx.fxml.FXML;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Level;

import static com.meti.app.client.InfinityClient.build;
import static com.meti.lib.javafx.Injector.readAsScene;
import static com.meti.lib.source.ObjectSource.from;
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
            int localPort = loadServer(state);
            loadClient(state, localPort);
        } catch (IOException e) {
            toolkit.getLogger().log(Level.WARNING, "Failed to build IO", e);
        }
    }

    private int loadServer(StateImpl state) throws IOException {
        int localPort = constructServer(state, new SocketSupplier(0));
        Scene serverDisplayScene = readAsScene(fromResource("/com/meti/app/control/ServerDisplay.fxml"), toolkit);
        loadStageManager(serverDisplayScene, toolkit.getStageManager(), 0);
        return localPort;
    }

    private void loadClient(StateImpl state, int localPort) throws IOException {
        constructClient(state, localPort);
        Scene clientDisplayScene = readAsScene(fromResource("/com/meti/app/control/ClientDisplay.fxml"), toolkit);
        loadStageManager(clientDisplayScene, toolkit.getStageManager(), toolkit.getStageManager().size() - 1);
    }

    private int constructServer(StateImpl state, PortSourceSupplier supplier) {
        Listener server = new InfinityServer(supplier);
        initListener(state, server);
        return supplier.getPort();
    }

    private void loadStageManager(Scene serverDisplayScene, StageManagerImpl stageManager, int index) {
        stageManager.ontoStage(serverDisplayScene, index);
    }

    private void constructClient(StateImpl state, int localPort) throws IOException {
        Listener client = build(from(new SocketSource(InetAddress.getByName("localhost"), localPort)), false);
        initListener(state, client);
    }

    private void initListener(StateImpl state, Listener client) {
        client.listen();
        state.add(client);
    }
}
