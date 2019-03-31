package com.meti.lib.net.source;

import com.meti.lib.trys.Catcher;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/31/2019
 */
public class SocketSourceSupplier implements SourceSupplier<SocketSource> {
    private final ServerSocket serverSocket;
    private final Catcher catcher;

    public SocketSourceSupplier(int port, Catcher catcher) throws IOException {
        this(new ServerSocket(port), catcher);
    }

    public SocketSourceSupplier(ServerSocket serverSocket, Catcher catcher) {
        this.serverSocket = serverSocket;
        this.catcher = catcher;
    }

    @Override
    public boolean isClosed() {
        return serverSocket.isClosed();
    }

    @Override
    public void close() throws IOException {
        serverSocket.close();
    }

    @Override
    public SocketSource get() {
        while (true) {
            try {
                return new SocketSource(serverSocket.accept());
            } catch (IOException e) {
                catcher.accept(e);
            }
        }
    }
}
