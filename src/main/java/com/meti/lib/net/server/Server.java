package com.meti.lib.net.server;

import java.io.Closeable;
import java.util.concurrent.Callable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public interface Server extends Callable<Void>, Closeable, Stoppable, Listener {
    int getPort();
}
