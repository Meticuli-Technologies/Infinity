package com.meti.lib.source;

import java.io.InputStream;

public interface ReadableSource<I extends InputStream> extends Source, Readable<I> {
}
