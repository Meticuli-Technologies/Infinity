package com.meti.handle;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MappedHandler implements TokenHandler {
    protected final Set<TokenHandler> handlers = new HashSet<>();

    protected MappedHandler(TokenHandler... initial) {
        handlers.addAll(Arrays.asList(initial));
    }

    private void addHandler(TokenHandler handler) {
        handlers.add(handler);
    }

    @Override
    public boolean canHandle(Object token) {
        return handlers.stream().anyMatch(tokenHandler -> tokenHandler.canHandle(token));
    }

    @Override
    public void handle(Object token) {
        handlers.parallelStream()
                .filter(tokenHandler -> tokenHandler.canHandle(token))
                .forEach(tokenHandler -> tokenHandler.handle(token));
    }

    public void addHandlers(Collection<? extends TokenHandler> handlers) {
        handlers.forEach(this::addHandler);
    }
}
