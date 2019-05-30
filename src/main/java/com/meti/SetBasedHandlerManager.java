package com.meti;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class SetBasedHandlerManager implements HandlerManager {
    private final Set<ResponseHandler> handlers = new HashSet<>();

    @Override
    public Set<Serializable> processResponse(Object response, Client client) throws Throwable {
        if (response instanceof Throwable) {
            throw (Throwable) response;
        } else {
            return processWithHandlers(response, client);
        }
    }

    @Override
    public Set<ResponseHandler> getHandlers() {
        return handlers;
    }

    private Set<Serializable> processWithHandlers(Object response, Client client) {
        return handlers.stream()
                .filter(handler -> handler.canHandle(response))
                .map(handler -> handler.handle(response, client))
                .flatMap(Optional::stream)
                .collect(Collectors.toSet());
    }
}