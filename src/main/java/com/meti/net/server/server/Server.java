package com.meti.net.server.server;

import com.meti.concurrent.Listener;
import com.meti.concurrent.Stoppable;

import java.io.Closeable;
import java.util.concurrent.Callable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public interface Server extends Callable<Void>, Closeable, Listener, Stoppable {
    int getPort();
}
