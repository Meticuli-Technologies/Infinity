package com.meti.lib.net.server;

import com.meti.lib.net.client.Client;
import com.meti.lib.net.client.SocketClient;
import com.meti.lib.net.client.handle.ResponseHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public class ServerSocketServer extends AbstractServer {
    private final ServerSocket serverSocket;

    public ServerSocketServer(int port, Collection<? extends ResponseHandler> initialHandlers) throws IOException {
        this(new ServerSocket(port), initialHandlers);
    }

    protected ServerSocketServer(ServerSocket serverSocket, Collection<? extends ResponseHandler> initialHandlers) {
        super(initialHandlers);
        this.serverSocket = serverSocket;
    }

    @Override
    public int getPort() {
        return serverSocket.getLocalPort();
    }

    @Override
    protected void preClose() throws IOException {
        serverSocket.close();
    }

    @Override
    protected Client acceptClient() throws IOException {
        Socket acceptedSocket = serverSocket.accept();
        return new SocketClient(acceptedSocket);
    }
}
