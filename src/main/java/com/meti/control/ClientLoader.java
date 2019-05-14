package com.meti.control;

import com.meti.net.client.Client;
import com.meti.net.source.SocketSource;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Objects;
import java.util.function.Consumer;

public class ClientLoader implements Constructable<Client> {
    private Consumer<Client> onClientConstructed;

    @Override
    public void setOnConstructed(Consumer<Client> onClientConstructed) {
        this.onClientConstructed = onClientConstructed;
    }

    Client construct(int localPort) throws IOException {
        Client client = new Client(new SocketSource(InetAddress.getByName("localhost"), localPort));
        Objects.requireNonNull(onClientConstructed).accept(client);
        return client;
    }
}