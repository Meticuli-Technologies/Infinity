package com.meti.lib.net.server;

import java.net.ServerSocket;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
class InfinityClientAcceptor extends ServerSocketClientAcceptor {
    InfinityClientAcceptor(ServerSocket serverSocket) {
        super(serverSocket, Set.of(new StringResponseHandler()));
    }
}
