package com.meti.app.core;

import com.meti.lib.net.MappedProcessor;
import com.meti.lib.net.Server;
import com.meti.lib.net.object.ObjectClient;
import com.meti.lib.net.object.ObjectSource;
import com.meti.lib.net.source.SocketSource;
import com.meti.lib.net.source.SocketSourceSupplier;
import com.meti.lib.net.source.Source;
import com.meti.lib.trys.Catcher;
import com.meti.lib.trys.TryableFactory;
import com.meti.lib.trys.TryableFunction;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import static com.meti.lib.trys.TryableFactory.DEFAULT_FACTORY;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/31/2019
 */
public class InfinityServer extends Server<ObjectSource, ObjectClient> {
    private final Consumer<Future<?>> futureConsumer;
    private final ExecutorService service;

    public InfinityServer(int port, Catcher catcher, Consumer<Future<?>> futureConsumer, ExecutorService service) throws IOException {
        super(new SocketSourceSupplier(port, catcher),
                DEFAULT_FACTORY.newFunction(ObjectSource::new),
                ObjectClient::new
        );
        this.futureConsumer = futureConsumer;
        this.service = service;
    }

    @Override
    protected void accept(ObjectClient client) {
        MappedProcessor processor = new MappedProcessor(client);
        futureConsumer.accept(service.submit(processor));
    }
}
