package com.meti.app.control;

import com.meti.app.core.runtime.InfinityState;
import com.meti.app.net.InfinityListener;
import com.meti.lib.net.ExecutorServiceListener;
import com.meti.lib.net.Client;
import com.meti.lib.net.Querier;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/26/2019
 */
public class MenuModel {
    private final InfinityState state;

    public MenuModel(InfinityState state) {
        this.state = state;
    }

    void nextImpl(int port) throws IOException {
        constructServerListener(port);
        constructClient(port);
    }

    private void constructServerListener(int port) throws IOException {
        InfinityListener serverListener = new InfinityListener(port, state.getExecutorServiceManager().service);
        serverListener.listen();
        state.add(serverListener);
    }

    private void constructClient(int port) throws IOException {
        Client client = new Client(new Socket(InetAddress.getByName("localhost"), port));
        state.add(client);

        constructQuerier(client);
    }

    private void constructQuerier(Client client) {
        Querier querier = new Querier(true, client);
        state.getExecutorServiceManager().service.submit(querier);
        state.add(querier);
    }
}
