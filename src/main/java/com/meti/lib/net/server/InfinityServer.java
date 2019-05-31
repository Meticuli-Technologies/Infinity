package com.meti.lib.net.server;

import java.net.ServerSocket;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
class InfinityServer extends ServerSocketServer {
    InfinityServer(ServerSocket serverSocket) {
        super(serverSocket, Set.of(new StringResponseHandler()));
    }
}
