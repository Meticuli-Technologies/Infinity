package com.meti.lib.net.client;

import java.io.Closeable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public interface Client extends Closeable, ObjectReader, ObjectWriter {
    String getName();
}
