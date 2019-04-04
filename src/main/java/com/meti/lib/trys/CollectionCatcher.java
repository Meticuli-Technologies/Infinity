package com.meti.lib.trys;

import com.meti.lib.util.ExceptionUtil;

import java.util.*;
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
            String delimiter = "\n\t";
            throw new Exception(collection
                    .stream()
                    .map(ExceptionUtil::writeException)
                    .collect(Collectors.joining(delimiter))
            );
        }
    }

}
