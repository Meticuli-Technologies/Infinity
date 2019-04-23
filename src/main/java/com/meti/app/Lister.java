package com.meti.app;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class Lister {
    Optional<String> listFrom(List<?> collection) {
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
