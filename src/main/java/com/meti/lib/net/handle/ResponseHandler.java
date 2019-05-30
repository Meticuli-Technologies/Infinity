package com.meti.lib.net.handle;

import com.meti.lib.net.client.Client;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public interface ResponseHandler {
    boolean canHandle(Object response);

    Optional<Serializable> handle(Object response, Client client);
}
