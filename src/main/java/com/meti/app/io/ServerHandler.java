package com.meti.app.io;

import com.meti.lib.io.server.handle.TokenHandler;
import com.meti.lib.io.source.Source;

import java.util.Collection;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/2/2019
 */
public interface ServerHandler {
    <S extends Source> Collection<? extends TokenHandler<Object, ?>> getHandlers(Class<S> sClass);
}
