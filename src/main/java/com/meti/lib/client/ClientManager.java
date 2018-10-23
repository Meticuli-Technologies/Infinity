package com.meti.lib.client;

import com.meti.lib.manage.Manager;

import java.net.InetAddress;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/21/2018
 */
public class ClientManager extends Manager<InetAddress, Client> {
    private final Consumer<Client> clientConsumer;
    private final Consumer<Exception> callback;

    public ClientManager(Consumer<Client> clientConsumer) {
        this(clientConsumer, Throwable::printStackTrace);
    }

    public ClientManager(Consumer<Client> clientConsumer, Consumer<Exception> callback) {
        this.clientConsumer = clientConsumer;
        this.callback = callback;
    }

    private void handleClient(Client client) {
        clientConsumer.accept(client);
    }

    @Override
    public boolean replace(InetAddress key, Client oldValue, Client newValue) {
        handleClient(newValue);
        return super.replace(key, oldValue, newValue);
    }

    @Override
    public Client replace(InetAddress key, Client value) {
        handleClient(value);
        return super.replace(key, value);
    }

    @Override
    public Client put(InetAddress key, Client value) {
        handleClient(value);
        return super.put(key, value);
    }

    @Override
    public void putAll(Map<? extends InetAddress, ? extends Client> m) {
        m.values().forEach(this::handleClient);
        super.putAll(m);
    }

    @Override
    public Client putIfAbsent(InetAddress key, Client value) {
        handleClient(value);
        return super.putIfAbsent(key, value);
    }
}
