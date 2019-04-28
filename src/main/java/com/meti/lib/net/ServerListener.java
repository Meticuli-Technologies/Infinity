package com.meti.lib.net;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class ServerListener implements Callable<Void> {
    private final Map<Predicate<Object>, Function<Object, Object>> resultMapper = new HashMap<>();
    private final Server server;

    public ServerListener(Server server) {
        this.server = server;
    }

    @Override
    public Void call() throws Exception {
        while (server.isOpen()) {
            nextClient(new Client(server.accept()));
        }
        return null;
    }

    public void nextClient(Client client) {
        process(new MappedProcessor(client, resultMapper));
    }

    public abstract void listen();
    protected abstract void process(Processor processor);
}
