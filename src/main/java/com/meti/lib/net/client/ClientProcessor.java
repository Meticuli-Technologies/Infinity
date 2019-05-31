package com.meti.lib.net.client;

import com.meti.lib.net.handle.HandlerManager;
import com.meti.lib.net.handle.ResponseHandler;
import com.meti.lib.net.handle.SetBasedHandlerManager;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

public class ClientProcessor implements ResponseProcessor {
    private final HandlerManager handlerManager = new SetBasedHandlerManager();
    private final Client client;

    public ClientProcessor(Client client) {
        this.client = client;
    }

    @Override
    public void processNextResponse() throws Throwable {
        Object nextResponse = nextResponse();
        Set<Serializable> serializablesToWrite = handlerManager.processResponse(nextResponse, client);
        client.writeAndFlushIterable(serializablesToWrite);
    }

    @Override
    public void addHandler(ResponseHandler handler) {
        handlerManager.getHandlers().add(handler);
    }

    @Override
    public void addHandlers(Collection<? extends ResponseHandler> handlers) {
        handlerManager.getHandlers().addAll(handlers);
    }

    private Object nextResponse() throws IOException, ClassNotFoundException {
        return client.read();
    }
}