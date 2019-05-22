package com.meti.lib.handle;

import com.meti.lib.source.ReadableSource;

import java.io.ObjectInputStream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/22/2019
 */
public interface MappedHandlerImpl<T, R extends ReadableSource<ObjectInputStream>> extends Handler<T, R> {
    void add(Handler<T, R> subHandler);
}
