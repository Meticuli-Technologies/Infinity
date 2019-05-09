package com.meti;

import java.util.HashSet;
import java.util.Set;

public class MappedHandler implements TokenHandler {
    private final Set<TokenHandler> handlers = new HashSet<>();

    @Override
    public boolean canHandle(Object token) {
        return handlers.stream().anyMatch(tokenHandler -> tokenHandler.canHandle(token));
    }

    @Override
    public Object handle(Object token) {
        return handlers.parallelStream()
                .filter(tokenHandler -> tokenHandler.canHandle(token))
                .map(tokenHandler -> tokenHandler.handle(token))
                .findAny()
                .orElseGet(() -> new IllegalArgumentException("No results found"));
    }
}
