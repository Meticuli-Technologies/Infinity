package com.meti.app.socket;

import com.meti.app.ConnectionFinderWizard;
import com.meti.lib.fx.ControllerLoader;
import com.meti.lib.net.Client;
import com.meti.lib.state.State;

import java.io.IOException;
import java.net.URL;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/29/2019
 */
public class SocketConnectionFinderWizard extends ConnectionFinderWizard {
    public SocketConnectionFinderWizard(State state) throws IOException {
        super("Sockets", ControllerLoader.load(getResource(), state));
    }

    private static URL getResource() {
        return SocketConnectionFinderWizard.class.getResource("/com/meti/app/socket/SocketConnectionFinderWizardDisplay.fxml");
    }

    @Override
    public Client getResult() {
        return null;
    }
}
