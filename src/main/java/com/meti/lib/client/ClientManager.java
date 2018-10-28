package com.meti.lib.client;

import com.meti.lib.server.ClientConsumer;
import com.meti.lib.manage.Manager;

import java.net.InetAddress;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/21/2018
 */
public class ClientManager<T extends Connection> extends Manager<InetAddress, Client<T>> {
    private final ClientConsumer clientConsumer;

    public ClientManager(ClientConsumer clientConsumer) {
        this(clientConsumer, Throwable::printStackTrace);
    }

    public ClientManager(ClientConsumer clientConsumer, Consumer<Exception> callback) {
        this.clientConsumer = clientConsumer;
        
        clientConsumer.setCallback(callback);
    }

    private void handleClient(Client<T> client) {
        clientConsumer.accept(client);
    }

    @Override
    public boolean replace(InetAddress key, Client<T> oldValue, Client<T> newValue) {
        handleClient(newValue);
        return super.replace(key, oldValue, newValue);
    }

    @Override
    public Client<T> replace(InetAddress key, Client<T> value) {
        handleClient(value);
        return super.replace(key, value);
    }

    @Override
    public Client<T> put(InetAddress key, Client<T> value) {
        handleClient(value);
        return super.put(key, value);
    }

    @Override
    public void putAll(Map<? extends InetAddress, ? extends Client<T>> m) {
        m.values().forEach(this::handleClient);
        super.putAll(m);
    }

    @Override
    public Client<T> putIfAbsent(InetAddress key, Client<T> value) {
        handleClient(value);
        return super.putIfAbsent(key, value);
    }
}
