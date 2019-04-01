package com.meti.app.control;

import com.meti.app.core.InfinityClient;
import com.meti.app.core.InfinityController;
import com.meti.app.core.InfinityServer;
import com.meti.lib.collection.State;
import com.meti.lib.net.Client;
import com.meti.lib.net.object.ObjectClient;
import com.meti.lib.net.object.ObjectSource;
import com.meti.lib.net.query.Querier;
import com.meti.lib.net.query.Query;
import com.meti.lib.net.source.SocketSource;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Future;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/31/2019
 */
public class Local extends InfinityController {
    @FXML
    private TextField portField;

    public Local(State state) {
        super(state);
    }

    @FXML
    public void back() {
        try {
            console.log(Level.INFO, "Returning to Menu.");
            onto(getClass().getResource("/com/meti/app/control/Menu.fxml"));
        } catch (IOException e) {
            console.log(Level.WARNING, e);
        }
    }

    @FXML
    public void next() {
        try {
            int port = Integer.parseInt(portField.getText());
            InfinityServer server = new InfinityServer(port, console.ofWarning(), state::add, service);
            Future<Void> serverFuture = service.submit(server);
            state.add(server);
            state.add(serverFuture);

            onto(getClass().getResource("/com/meti/app/control/ServerDisplay.fxml"), 0);

            InfinityClient client = new InfinityClient(InetAddress.getByName("localhost"), port);
            Querier querier = new Querier(client);
            Future<Void> querierFuture = service.submit(querier);
            state.add(client);
            state.add(querier);
            state.add(querierFuture);

            onto(getClass().getResource("/com/meti/app/control/ClientDisplay.fxml"), 1);
        } catch (IOException e) {
            console.log(Level.SEVERE, e);
        }
    }
}
