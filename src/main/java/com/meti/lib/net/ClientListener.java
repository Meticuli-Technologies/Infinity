package com.meti.lib.net;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Predicate;

abstract class ClientListener implements Callable<Void> {
    private final Map<Predicate<Object>, Function<Object, Object>> resultMapper = new HashMap<>();
    private final Server server;

    public ClientListener(Server server) {
        this.server = server;
    }

    @Override
    public Void call() throws Exception {
        while (server.isOpen()) {
            Client client = new Client(server.accept());
            process(new Processor(client, resultMapper));
        }
        return null;
    }

    protected abstract void process(Processor processor);
}
