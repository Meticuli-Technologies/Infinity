package com.meti;

import java.io.Serializable;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public interface HandlerManager {
    Set<Serializable> processResponse(Object response, Client client) throws Throwable;

    Set<ResponseHandler> getHandlers();
}
