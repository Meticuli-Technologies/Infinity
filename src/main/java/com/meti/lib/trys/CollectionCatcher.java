package com.meti.lib.trys;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/31/2019
 */
public class CollectionCatcher<C extends Collection<Exception>> implements Catcher {
    private final C collection;

    public CollectionCatcher(C collection) {
        this.collection = collection;
    }

    @Override
    public void accept(Exception e) {
        collection.add(e);
    }

    public static CollectionCatcher<List<Exception>> list() {
        return new CollectionCatcher<>(new ArrayList<>());
    }

    public static CollectionCatcher<Set<Exception>> set() {
        return new CollectionCatcher<>(new HashSet<>());
    }

    public void throwAll() throws Exception {
        if (!collection.isEmpty()) {
            throw new Exception(collection
                    .stream()
                    .map(e -> {
                        StringWriter writer = new StringWriter();
                        e.printStackTrace(new PrintWriter(writer));
                        return writer.toString();
                    })
                    .collect(Collectors.joining("\n\t"))
            );
        }
    }
}
