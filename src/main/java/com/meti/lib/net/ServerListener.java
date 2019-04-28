package com.meti.lib.net;

import java.util.concurrent.Callable;

public abstract class ServerListener implements Callable<Void> {
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
        process(createProcessor(client));
    }

    public abstract void listen();

    protected abstract Processor createProcessor(Client client);
    protected abstract void process(Processor processor);
}
