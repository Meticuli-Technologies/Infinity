package com.meti.app.client;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SocketClientBootstrap implements ClientBootstrap {
    private final InetAddress address;
    private final int port;

    public SocketClientBootstrap(int port) throws UnknownHostException {
        this(InetAddress.getLocalHost(), port);
    }

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
