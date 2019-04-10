package com.meti.lib.collect.catches;

import java.util.Collection;

public class CollectionCatcher<C extends Collection<Exception>> implements Catcher {
    public final C collection;

    public CollectionCatcher(C collection) {
        this.collection = collection;
    }

    @Override
    public void accept(Exception e) {
        collection.add(e);
    }
}
