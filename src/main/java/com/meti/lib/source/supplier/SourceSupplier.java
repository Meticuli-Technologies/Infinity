package com.meti.lib.source.supplier;

import com.meti.lib.source.ComplexSource;
import com.meti.lib.source.Source;

import java.io.InputStream;
import java.io.OutputStream;

public interface SourceSupplier<I extends InputStream, O extends OutputStream, S extends ComplexSource<I, O>> extends Source {
    S next();
}
