package com.meti;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class SetBasedHandlerManager implements HandlerManager {
    private final Set<ResponseHandler> handlers = new HashSet<>();

    @Override
    public void processResponse(Object response) throws Throwable {
        if (response instanceof Throwable) {
            throw (Throwable) response;
        } else {
            processResponseNonThrowable(response);
        }
    }

    @Override
    public Set<ResponseHandler> getHandlers() {
        return handlers;
    }

    private void processResponseNonThrowable(Object response) {
        boolean noHandlerFound = !processWithHandlers(response)
                .findAny()
                .isPresent();
        if (noHandlerFound) {
            throw new UnsupportedOperationException("Unknown response: " + response);
        }
    }

    private Stream<ResponseHandler> processWithHandlers(Object response) {
        return handlers.stream()
                .filter(handler -> handler.canHandle(response))
                .peek(handler -> handler.handle(response, null));
    }
}