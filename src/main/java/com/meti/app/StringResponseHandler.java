package com.meti.app;

import com.meti.lib.net.client.Client;
import com.meti.lib.net.client.handle.ResponseHandler;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public class StringResponseHandler implements ResponseHandler {
    @Override
    public boolean canHandle(Object response) {
        return response instanceof String;
    }

    @Override
    public Optional<Serializable> handle(Object response, Client client) {
        return Optional.of(client.getName() + ": " + response);
    }
}
