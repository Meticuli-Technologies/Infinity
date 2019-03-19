package com.meti.app.net;

import com.meti.lib.net.Client;
import com.meti.lib.net.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InfinityServer extends Server {
    private final ExecutorService service = Executors.newCachedThreadPool();

    public InfinityServer(int port) throws IOException {
        super(new ServerSocket(port));
    }

    @Override
    public void handleAccept(Socket accept) throws Exception {
        service.submit(new Client(accept));
    }
}
