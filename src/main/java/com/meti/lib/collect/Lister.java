package com.meti.lib.collect;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Lister {
    public Optional<String> listFrom(List<?> collection) {
        if (collection.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(joinStream(collection));
        }
    }

    private String joinStream(List<?> collection) {
        return collection.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n\t"));
    }
}
