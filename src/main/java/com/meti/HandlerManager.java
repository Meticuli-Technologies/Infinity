package com.meti;

import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public interface HandlerManager {
    void processResponse(Object response) throws Throwable;

    Set<ResponseHandler> getHandlers();
}
