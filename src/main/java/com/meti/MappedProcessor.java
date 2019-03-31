package com.meti;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/30/2019
 */
public class MappedProcessor extends Processor {
    protected final Set<Handler<Object>> handlers = new HashSet<>();

    public MappedProcessor(ObjectClient client) {
        super(client);
    }

    @Override
    public Object processToken(Object token) {
        Set<Object> collect = handlers.stream()
                .filter(objectHandler -> objectHandler.test(token))
                .map(objectHandler -> objectHandler.apply(token))
                .collect(Collectors.toSet());
        return toSingle(collect);
    }

    private static <T> Optional<T> toSingle(Collection<T> collection) {
        if (collection.isEmpty()) {
            return Optional.empty();
        }

        if (collection.size() > 1) {
            throw new IllegalArgumentException("Size must be equal to 1.");
        }

        return Optional.of(new ArrayList<>(collection).get(0));
    }
}
