package com.meti.net.source;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/30/2019
 */
public interface Source<I extends InputStream, O extends OutputStream> extends Closeable {
    I getInputStream();

    O getOutputStream();

    boolean isClosed();
}
