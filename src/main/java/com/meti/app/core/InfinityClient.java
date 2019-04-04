package com.meti.app.core;

import com.meti.lib.net.object.ObjectClient;
import com.meti.lib.net.object.ObjectSource;
import com.meti.lib.net.source.SocketSource;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class InfinityClient extends ObjectClient {
    public InfinityClient(InetAddress address, int port) throws IOException {
        super(new ObjectSource(new SocketSource(new Socket(address, port))));
    }
}
