package com.meti;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class SetBasedHandlerManager implements HandlerManager {
    private final Set<ResponseHandler> handlers = new HashSet<>();

    @Override
    public Set<Serializable> processResponse(Object response) throws Throwable {
        if (response instanceof Throwable) {
            throw (Throwable) response;
        } else {
            return processResponseNonThrowable(response);
        }
    }

    @Override
    public Set<ResponseHandler> getHandlers() {
        return handlers;
    }

    private Set<Serializable> processResponseNonThrowable(Object response) {
        Set<Serializable> responses = processWithHandlers(response);
        if (responses.isEmpty()) {
            throw new UnsupportedOperationException("Unknown response: " + response);
        }
        return responses;
    }

    private Set<Serializable> processWithHandlers(Object response) {
        return handlers.stream()
                .filter(handler -> handler.canHandle(response))
                .map(handler -> handler.handle(response, null))
                .flatMap(Optional::stream)
                .collect(Collectors.toSet());
    }
}