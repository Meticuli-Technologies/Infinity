package com.meti.app;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class Lister {
    Optional<String> createTaskString(List<?> collection) {
        if (collection.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(createTaskListString(collection));
        }
    }

    private String createTaskListString(List<?> collection) {
        return collection.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n\t"));
    }
}
