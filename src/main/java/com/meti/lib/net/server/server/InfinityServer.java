package com.meti.lib.net.server.server;

import com.meti.app.server.StringResponseHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public class InfinityServer extends ServerSocketServer {
    public InfinityServer(int port) throws IOException {
        this(new ServerSocket(port));
    }

    private InfinityServer(ServerSocket serverSocket) {
        super(serverSocket, Set.of(new StringResponseHandler()));
    }
}
