package com.meti.lib.trys;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/31/2019
 */
public class CollectionCatcher<C extends Collection<Exception>> extends CollectionConsumer<Exception, C> implements Catcher {

    public CollectionCatcher(C collection) {
        super(collection);
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
