package com.meti.app.client;

import java.net.InetAddress;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/3/2019
 */
public interface ClientBootstrap {
    InetAddress getAddress();

    int getPort();
}
