package com.meti.control;

import com.meti.concurrent.ExecutorServiceManager;
import com.meti.net.server.InfinityServerHandler;
import com.meti.net.server.Server;
import com.meti.net.source.SocketSourceSupplier;

import java.util.Objects;
import java.util.function.Consumer;

public class ServerLoader implements Constructable<Server> {
    private Consumer<Server> onConstructed;

    @Override
    public void setOnConstructed(Consumer<Server> onConstructed) {
        this.onConstructed = onConstructed;
    }

    void construct(SocketSourceSupplier supplier, ExecutorServiceManager executorServiceManager) {
        Server server = new Server(supplier, executorServiceManager, new InfinityServerHandler()).listen();
        Objects.requireNonNull(onConstructed).accept(server);
    }
}
