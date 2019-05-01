package com.meti.lib.io.source.supplier;

import com.meti.lib.io.source.Source;

import java.util.function.Supplier;

public interface SourceSupplier<S extends Source> extends Supplier<S> {

    boolean isOpen();
}
