package com.meti.lib.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ClientLauncher {
    public static Client launch(InetAddress address, int port) throws IOException {
        return new Client(new Socket(address, port));
    }
}
