package com.meti.lib.source;

import java.io.InputStream;
import java.io.OutputStream;

public interface ComplexSource<I extends InputStream, O extends OutputStream> extends Source, Readable<I>, Writable<O> {
}
