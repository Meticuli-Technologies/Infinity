package com.meti.lib.source;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SourceSupplier<I extends InputStream, O extends OutputStream, S extends CompoundSource<I, O>> extends Source {
    S next() throws IOException;
}
