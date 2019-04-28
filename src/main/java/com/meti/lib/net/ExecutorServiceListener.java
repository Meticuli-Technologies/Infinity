package com.meti.lib.net;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/26/2019
 */
public class ExecutorServiceListener extends ServerListener {
    private final ExecutorService service;
    private final Map<Predicate<Object>, Function<Object, Object>> resultMapper = new HashMap<>();

    public ExecutorServiceListener(int port, ExecutorService service) throws IOException {
        super(new Server(port));
        this.service = service;
        this.service.submit(this);
    }

    public void listen(){
        service.submit(this);
    }

    @Override
    protected void process(Processor processor) {
        service.submit(processor);
    }

    @Override
    protected Processor createProcessor(Client client) {
        return new MappedProcessor(client, resultMapper);
    }
}
