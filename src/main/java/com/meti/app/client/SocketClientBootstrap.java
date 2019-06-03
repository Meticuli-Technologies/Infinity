package com.meti.app.client;

import java.net.InetAddress;

public class SocketClientBootstrap implements ClientBootstrap {
    private final InetAddress address;
    private final int port;

    public SocketClientBootstrap(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    @Override
    public InetAddress getAddress() {
        return address;
    }

    @Override
    public int getPort() {
        return port;
    }
}
