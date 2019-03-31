package com.meti.lib.trys;

import java.util.Set;
import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/31/2019
 */
public class TryableFactory<C extends Catcher> {
    public static final TryableFactory<CollectionCatcher<Set<Exception>>> DEFAULT_FACTORY = new TryableFactory<>(CollectionCatcher.set());
    public final C catcher;

    public TryableFactory(C catcher) {
        this.catcher = catcher;
    }

    public <T> Consumer<T> newConsumer(TryableConsumer<T> tryableConsumer) {
        return t -> {
            try {
                tryableConsumer.accept(t);
            } catch (Exception e) {
                catcher.accept(e);
            }
        };
    }
}
