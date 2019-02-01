package com.meti.app.control.connect;

import com.meti.lib.fx.ControllerLoader;
import com.meti.lib.fx.FXWizard;
import com.meti.lib.net.Connection;
import com.meti.lib.state.State;

import java.io.IOException;
import java.net.URL;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/31/2019
 */
class ConnectionAdderWizard extends FXWizard<Connection<?, ?, ?>> {
    public ConnectionAdderWizard(URL connectionAdderURL, State state) throws IOException {
        super("Connection Adder", ControllerLoader.load(connectionAdderURL, state));
    }

    @Override
    public Connection<?, ?, ?> getResult() {
        return null;
    }
}