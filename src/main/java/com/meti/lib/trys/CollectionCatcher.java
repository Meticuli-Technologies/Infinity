package com.meti.lib.trys;

import java.util.Collection;

import static com.meti.lib.util.ExceptionUtil.compound;

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
            throw compound(collection);
        }
    }
}
