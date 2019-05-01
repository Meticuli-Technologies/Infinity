package com.meti.app.control.menu;

import com.meti.app.io.InfinityClient;
import com.meti.app.io.InfinityServer;
import com.meti.lib.io.*;
import com.meti.lib.io.channel.ObjectChannel;
import com.meti.lib.io.source.supplier.ServerSocketSupplier;
import com.meti.lib.io.source.SocketSource;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

class MenuModel {
    private final Menu menu;

    public MenuModel(Menu menu) {
        this.menu = menu;
    }

    void setupIO() throws IOException {
        int port = Integer.parseInt(menu.portField.getText());
        setupServer(port);
        setupClient(port);
        //TODO: do something with futures
    }

    private void setupServer(int port) throws IOException {
        InfinityServer server = new InfinityServer(new ServerSocketSupplier(new ServerSocket(port)));
        menu.state.add(server);
        menu.serviceManager.service.submit(server.getListener());
    }

    private void setupClient(int port) throws IOException {
        InfinityClient client = new InfinityClient(new SocketSource(new Socket(InetAddress.getByName("localhost"), port)));
        ObjectChannel channel = client.getChannel(true);
        Querier querier = new Querier(channel);
        menu.serviceManager.service.submit(querier.getListener());
        menu.state.addAll(Arrays.asList(client, channel, querier));
    }
}