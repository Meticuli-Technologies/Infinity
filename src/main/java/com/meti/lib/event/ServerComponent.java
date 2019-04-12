package com.meti.lib.event;

import com.meti.lib.net.Client;
import com.meti.lib.net.Handler;

import java.util.stream.Stream;

public abstract class ServerComponent<E extends Event, T> extends Component<E, T> {
    public abstract Stream<? extends Handler<Object>> getHandlers(Client client);
}
