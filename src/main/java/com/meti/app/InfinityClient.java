package com.meti.app;

import com.meti.lib.Client;
import com.meti.lib.Handler;
import com.meti.lib.HandlerMap;

import java.io.IOException;
import java.net.Socket;
import java.util.stream.Stream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/12/2019
 */
public class InfinityClient extends Client  {
    private final HandlerMap<Object> tokenMap = new HandlerMap<>();

    public InfinityClient(Socket socket) throws IOException {
        super(socket);
    }

    @Override
    protected void handleObject(Object token) {
        Stream<Handler<Object>> stream = tokenMap.process(token);
        if (stream.count() == 0) {
            throw new IllegalArgumentException("No handlers for " + token);
        }
    }
}
