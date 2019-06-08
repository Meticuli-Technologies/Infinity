package com.meti.lib.asset.source;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public interface Source {
    InputStream newInputStream() throws IOException;

    String getName();

    OutputStream newOutputStream() throws IOException;
}
