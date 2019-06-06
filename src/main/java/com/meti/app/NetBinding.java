package com.meti.app;

import com.meti.lib.net.client.Client;
import com.meti.lib.net.server.Server;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public interface NetBinding {
    Client getClient();

    Server getServer();
}
