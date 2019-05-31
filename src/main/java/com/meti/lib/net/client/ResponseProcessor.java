package com.meti.lib.net.client;

import com.meti.lib.net.handle.ResponseHandler;

import java.util.Collection;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public interface ResponseProcessor {
    void processNextResponse() throws Throwable;

    void addHandler(ResponseHandler handler);

    void addHandlers(Collection<? extends ResponseHandler> handlers);

    Set<ResponseHandler> getHandlers();
}
