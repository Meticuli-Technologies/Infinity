package com.meti.lib.source;

import java.io.IOException;

public interface SourceSupplier<S extends CompoundSource<?, ?>> extends Source {
    S next() throws IOException;
}
