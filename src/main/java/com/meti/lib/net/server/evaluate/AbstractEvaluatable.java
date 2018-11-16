package com.meti.lib.net.server.evaluate;

import com.meti.lib.net.client.Client;
import com.meti.lib.net.server.Server;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/16/2018
 */
public abstract class AbstractEvaluatable<T> implements Evaluatable<T> {
    protected Server server;
    protected Client client;

    public void setServer(Server server) {
        this.server = server;
    }

    public void setClient(Client<?> client) {
        this.client = client;
    }
}
