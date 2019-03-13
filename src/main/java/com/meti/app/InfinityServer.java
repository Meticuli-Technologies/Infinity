package com.meti.app;

import com.meti.lib.Server;

import java.net.ServerSocket;
import java.util.Objects;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/12/2019
 */
public class InfinityServer extends Server<InfinityClient> {
    public InfinityServer(ServerSocket serverSocket) {
        super(InfinityClient.builder, serverSocket);
    }

    @Override
    public void handleClient(InfinityClient client) {
        Objects.requireNonNull(client);
    }
}
