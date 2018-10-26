package com.meti.lib.client;

import com.meti.lib.fx.StateDependency;

import java.net.InetAddress;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/25/2018
 */
public class ClientDependency extends StateDependency {
    private Client client;

    public void loadAddress(InetAddress address) {
        Client client = controllerState.getObject(ClientState.class).clientMap.get(address);
        if (client == null) {
            throw new IllegalArgumentException("Client not found");
        }
        this.client = client;
    }
}
