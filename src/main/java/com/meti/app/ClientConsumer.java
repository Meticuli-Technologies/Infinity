package com.meti.app;

import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public abstract class ClientConsumer implements Consumer<Client> {
    private final Consumer<Throwable> callback;

    public ClientConsumer() {
        this(Throwable::printStackTrace);
    }

    public ClientConsumer(Consumer<Throwable> callback) {
        this.callback = callback;
    }

    @Override
    public void accept(Client client) {
        try {
            acceptClient(client);
        } catch (Throwable throwable) {
            callback.accept(throwable);
        }
    }

    public abstract void acceptClient(Client client) throws Throwable;
}
