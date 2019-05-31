package com.meti.net.client;

import com.meti.net.handle.ResponseHandler;

import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public interface ResponseProcessor {
    void processNextResponse() throws Throwable;

    Set<ResponseHandler> getHandlers();
}
