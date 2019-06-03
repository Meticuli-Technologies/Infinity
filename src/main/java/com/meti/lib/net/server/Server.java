package com.meti.lib.net.server;

import com.meti.lib.concurrent.Listener;
import com.meti.lib.net.client.Client;
import com.meti.lib.net.client.handle.ResponseHandler;

import java.io.Closeable;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public interface Server extends Callable<Void>, Closeable, Listener {
    Set<Client> getClients();

    Set<ResponseHandler> getResponseHandlers();
    int getPort();
}
