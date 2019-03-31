package com.meti.app.core;

import com.meti.lib.net.MappedProcessor;
import com.meti.lib.net.Server;
import com.meti.lib.net.object.ObjectClient;
import com.meti.lib.net.object.ObjectSource;
import com.meti.lib.net.source.SocketSourceSupplier;
import com.meti.lib.trys.Catcher;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/31/2019
 */
public class InfinityServer extends Server<ObjectSource, ObjectClient> {
    private final ExecutorService service;

    public InfinityServer(int port, Catcher catcher, ExecutorService service) throws IOException {
        super(new SocketSourceSupplier(port, catcher),
                source -> {
                    try {
                        return new ObjectSource(source);
                    } catch (IOException e) {
                        return null;
                    }
                },
                ObjectClient::new
        );
        this.service = service;
    }

    @Override
    public void accept(ObjectClient client) {
        MappedProcessor processor = new MappedProcessor(client);
        service.submit(processor);
        //TODO: do something with the future
    }
}
