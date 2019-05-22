package com.meti.app.control;

import com.meti.app.core.state.StateImpl;
import com.meti.app.core.state.Toolkit;
import com.meti.app.server.InfinityServer;
import com.meti.lib.net.Listener;
import com.meti.lib.source.SocketSupplier;
import javafx.fxml.FXML;

import java.io.IOException;

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
    public void local() throws IOException {
        StateImpl state = toolkit.getState();
        constructServer(state);

    }

    private void constructServer(StateImpl state) throws IOException {
        Listener server = new InfinityServer(new SocketSupplier(0));
        server.listen();
        state.add(server);
    }
}
