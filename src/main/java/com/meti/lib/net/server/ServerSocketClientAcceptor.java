package com.meti.lib.net.server;

import com.meti.lib.net.client.Client;
import com.meti.lib.net.client.SocketClient;
import com.meti.lib.net.handle.ResponseHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public abstract class ServerSocketClientAcceptor extends ClientAcceptorImpl {
    private final ServerSocket serverSocket;

    ServerSocketClientAcceptor(ServerSocket serverSocket, Collection<? extends ResponseHandler> initialHandlers) {
        super(initialHandlers);
        this.serverSocket = serverSocket;
    }


    @Override
    public void close() throws IOException {
        super.close();
        serverSocket.close();
    }

    @Override
    protected Client acceptClient() throws IOException {
        Socket acceptedSocket = serverSocket.accept();
        return new SocketClient(acceptedSocket);
    }
}
