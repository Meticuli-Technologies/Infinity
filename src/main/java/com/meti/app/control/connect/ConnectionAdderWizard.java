package com.meti.app.control.connect;

import com.meti.lib.fx.ControllerLoader;
import com.meti.lib.fx.FXWizard;
import com.meti.lib.net.Connection;
import com.meti.lib.state.State;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/31/2019
 */
class ConnectionAdderWizard extends FXWizard<Connection<?, ?, ?>> {
    public ConnectionAdderWizard(URL connectionAdderURL, Consumer<Parent> handler, State state) throws IOException {
        super("Connection Adder", handler, state, ControllerLoader.load(connectionAdderURL, state));
    }

    @Override
    public Connection<?, ?, ?> getResult() {
        return null;
    }
}