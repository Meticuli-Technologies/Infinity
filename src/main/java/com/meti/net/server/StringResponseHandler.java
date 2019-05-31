package com.meti.net.server;

import com.meti.net.client.Client;
import com.meti.net.handle.ResponseHandler;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
class StringResponseHandler implements ResponseHandler {
    @Override
    public boolean canHandle(Object response) {
        return response instanceof String;
    }

    @Override
    public Optional<Serializable> handle(Object response, Client client) {
        return Optional.of(client.getName() + ": " + response);
    }
}
