package com.meti.lib.client;

import com.meti.lib.fx.StateDependency;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.net.InetAddress;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/25/2018
 */
public class ClientDependency extends StateDependency {
    public final ObjectProperty<Client> clientProperty = new SimpleObjectProperty<>();

    public Client loadAddress(InetAddress address) {
        Client client = controllerState.getObject(ClientState.class).clientMap.get(address);
        if (client == null) {
            throw new IllegalArgumentException("Client not found");
        }

        clientProperty.set(client);
        return client;
    }
}
