package com.meti.app.control.menu;

import com.meti.app.io.InfinityClient;
import com.meti.app.io.InfinityServer;
import com.meti.lib.io.Querier;
import com.meti.lib.io.channel.ObjectChannel;
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
        ObjectChannel channel = client.getChannel(true);
        Querier querier = new Querier(channel);
        menu.state.addAll(Arrays.asList(client, channel, querier));
        return menu.serviceManager.service.submit(querier.getListener());
    }

    Future<Server> setupServer(int port) throws IOException {
        InfinityServer server = new InfinityServer(new ServerSocketSupplier(new ServerSocket(port)));
        menu.state.add(server);
        return menu.serviceManager.service.submit(server.getListener());
    }
}