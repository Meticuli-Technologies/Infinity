package com.meti.lib.net.client;

import com.meti.lib.net.connect.ObjectConnection;
import com.meti.lib.net.server.Server;

import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public abstract class ClientConsumer<T extends ObjectConnection> implements Consumer<Client<T>> {
    private final Consumer<Throwable> callback;
    protected Server server;

    protected ClientConsumer() {
        this(Throwable::printStackTrace);
    }

    private ClientConsumer(Consumer<Throwable> callback) {
        this.callback = callback;
    }

    @Override
    public void accept(Client<T> client) {
        try {
            acceptClient(client);
        } catch (Throwable throwable) {
            callback.accept(throwable);
        }
    }

    protected abstract void acceptClient(Client<T> client) throws Throwable;

    public void setServer(Server server) {
        this.server = server;
    }
}
