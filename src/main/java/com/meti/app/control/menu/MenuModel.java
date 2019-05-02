package com.meti.app.control.menu;

import com.meti.app.io.InfinityClient;
import com.meti.app.io.InfinityServer;
import com.meti.app.io.update.client.MappedUpdater;
import com.meti.app.io.update.client.Updater;
import com.meti.lib.io.query.Querier;
import com.meti.lib.io.server.Server;
import com.meti.lib.io.source.SocketSource;
import com.meti.lib.io.source.supplier.ServerSocketSupplier;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.Future;

class MenuModel {
    private final Menu menu;

    MenuModel(Menu menu) {
        this.menu = menu;
    }

    Future<Querier> setupClient(int port) throws IOException {
        InfinityClient client = new InfinityClient(new SocketSource(new Socket(InetAddress.getByName("localhost"), port)));
        Querier querier = client.getQuerier(true);
        Updater updater = new MappedUpdater(querier);
        menu.state.addAll(Arrays.asList(client, querier, updater));
        return menu.serviceManager.service.submit(querier.getListener());
    }

    Future<Server> setupServer(int port) throws IOException {
        InfinityServer server = new InfinityServer(new ServerSocketSupplier(new ServerSocket(port)), menu.state);
        menu.state.add(server);
        return menu.serviceManager.service.submit(server.getListener());
    }
}