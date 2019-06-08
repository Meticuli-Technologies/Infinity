package com.meti.app.server;

import com.meti.app.Toolkit;
import com.meti.lib.net.client.handle.ResponseHandler;

import java.util.Collection;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/8/2019
 */
public interface ServerHandlerSupplier {
    Collection<? extends ResponseHandler> getHandlers(Toolkit toolkit);
}
