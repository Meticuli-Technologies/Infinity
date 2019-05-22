package com.meti.lib.net;

import com.meti.lib.source.CompoundSource;
import com.meti.lib.source.SourceSupplier;

import java.io.Closeable;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/22/2019
 */
public interface ServerImpl<S extends CompoundSource<?, ?>, O extends SourceSupplier<S>> extends Callable<O>, Closeable {
    Consumer<S> getOnAccept();

    void setOnAccept(Consumer<S> onAccept);

    int getPort();
}