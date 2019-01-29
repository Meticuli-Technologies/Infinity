package com.meti.app.socket;

import com.meti.app.ConnectionManagerWizard;
import com.meti.lib.fx.ControllerLoader;
import com.meti.lib.fx.Wizard;
import com.meti.lib.net.Client;
import com.meti.lib.state.State;

import java.io.IOException;
import java.net.URL;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/29/2019
 */
public class SocketConnectionManagerWizard extends ConnectionManagerWizard {
    public Wizard<Client> load(State state) throws IOException {
        return super.load("Sockets", ControllerLoader.load(getResource(), state));
    }

    private static URL getResource() {
        return SocketConnectionManagerWizard.class.getResource("/com/meti/app/socket/SocketConnectionFinderWizardDisplay.fxml");
    }

    @Override
    public Client getResult() {
        return null;
    }
}
