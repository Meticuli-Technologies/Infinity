package com.meti.app.control.menu;

import com.meti.app.ExecutorServiceManager;
import com.meti.app.core.runtime.InfinityState;
import com.meti.app.io.InfinityClient;
import com.meti.app.io.InfinityServer;
import com.meti.app.io.update.client.MappedUpdater;
import com.meti.app.io.update.client.Updater;
import com.meti.lib.io.query.Querier;
import com.meti.lib.io.source.SocketSource;
import com.meti.lib.io.source.supplier.ServerSocketSupplier;
import com.meti.lib.module.ModuleManager;
import com.meti.lib.util.tryable.TryableFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

class MenuModel {
    private final ExecutorServiceManager serviceManager;

    public MenuModel(ExecutorServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    List<Object> setupClient(int port) throws IOException {
        InfinityClient client = new InfinityClient(new SocketSource(new Socket(InetAddress.getByName("localhost"), port)));
        Querier querier = client.getQuerier(false);
        Updater updater = new MappedUpdater(querier);
        serviceManager.submit(querier.getListener());
        return Arrays.asList(client, querier, updater);
    }

    InfinityServer setupServer(int port, TryableFactory factory, ModuleManager moduleManager) throws IOException {
        InfinityServer server = new InfinityServer(new ServerSocketSupplier(new ServerSocket(port)), factory, moduleManager);
        serviceManager.submit(server.getListener());
        return server;
    }
}