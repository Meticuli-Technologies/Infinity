package com.meti.lib.source;

import java.io.InputStream;
import java.io.OutputStream;

public interface CompoundSource<I extends InputStream, O extends OutputStream> extends ReadableSource<I>, WritableSource<O> {
}
