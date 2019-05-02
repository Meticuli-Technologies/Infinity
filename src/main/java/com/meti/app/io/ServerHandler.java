package com.meti.app.io;

import com.meti.app.io.update.server.UpdateManager;
import com.meti.lib.io.server.handle.TokenHandler;

import java.util.Collection;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/2/2019
 */
public interface ServerHandler {
    Collection<? extends TokenHandler<Object, ?>> getHandlers(UpdateManager updateManager);
}
