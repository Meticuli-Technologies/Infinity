package com.meti.handle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MappedHandler implements TokenHandler {
    public final Set<TokenHandler> handlers = new HashSet<>();

    public MappedHandler(TokenHandler... initial) {
        handlers.addAll(Arrays.asList(initial));
    }

    public void addHandler(TokenHandler handler) {
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
}
