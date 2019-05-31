package com.meti.net.server.server;

import java.io.Closeable;
import java.util.concurrent.Callable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public interface Server extends Callable<Void>, Closeable {
    int getPort();
}
