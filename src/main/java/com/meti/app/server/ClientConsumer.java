package com.meti.app.server;

import com.meti.lib.client.Client;

import java.util.function.Consumer;

public class ClientConsumer implements Consumer<Client> {
    private Consumer<Exception> callback;

    @Override
    public void accept(Client client) {
        //TODO: server-side code
    }

    public void setCallback(Consumer<Exception> callback) {
        this.callback = callback;
    }
}
