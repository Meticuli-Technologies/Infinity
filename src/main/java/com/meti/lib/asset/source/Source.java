package com.meti.lib.asset.source;

import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public interface Source extends Closeable {
    InputStream getInputStream();

    String getName();

    OutputStream getOutputStream();
}
