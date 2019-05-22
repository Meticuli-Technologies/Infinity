package com.meti.lib.source;

import java.io.OutputStream;

public interface WritableSource<O extends OutputStream> extends Source, Writable<O> {
}
