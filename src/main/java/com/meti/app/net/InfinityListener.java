package com.meti.app.net;

import com.meti.lib.net.Client;
import com.meti.lib.net.ExecutorServiceListener;
import com.meti.lib.net.MappedProcessor;
import com.meti.lib.net.Processor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/28/2019
 */
public class InfinityListener extends ExecutorServiceListener {
    private final Map<Predicate<Object>, Function<Object, Object>> resultMapper = new HashMap<>();

    public InfinityListener(int port, ExecutorService service) throws IOException {
        super(port, service);
    }

    @Override
    protected Processor createProcessor(Client client) {
        return new MappedProcessor(client, resultMapper);
    }
}
