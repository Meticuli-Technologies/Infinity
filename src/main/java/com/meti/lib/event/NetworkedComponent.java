package com.meti.lib.event;

import com.meti.lib.net.Client;

import java.util.HashMap;
import java.util.Map;

public class NetworkedComponent<E extends Event> extends Component<E> {
    protected final Map<Client<?>, Updater<E>> updaterMap = new HashMap<>();

    public Updater<E> confirm(Client<?> client) {
        Updater<E> updater;
        if (updaterMap.containsKey(client)) {
            updater = updaterMap.get(client);
        } else {
            updater = new Updater<>(client, eventManager);
            updaterMap.put(client, updater);
        }
        return updater;
    }
}
