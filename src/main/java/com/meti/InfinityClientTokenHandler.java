package com.meti;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class InfinityClientTokenHandler extends MappedHandler {
    public InfinityClientTokenHandler(Set<ClientComponent> components) {
        handlers.addAll(getHandlersFrom(components));
    }

    private Set<? extends TokenHandler> getHandlersFrom(Set<ClientComponent> components) {
        return components.stream()
                .map(ClientComponent::getHandlers)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }
}
