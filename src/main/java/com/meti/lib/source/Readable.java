package com.meti.lib.source;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public interface Readable<I extends InputStream> extends Closeable {
    I getInputStream() throws IOException;
}
