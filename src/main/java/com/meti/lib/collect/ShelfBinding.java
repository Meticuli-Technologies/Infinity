package com.meti.lib.collect;

import java.util.HashSet;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/19/2018
 */
public class ShelfBinding<O, P> {
    public final Set<O> content = new HashSet<>();
    public final P predicate;

    private ShelfBinding(P predicate) {
        this.predicate = predicate;
    }
}
