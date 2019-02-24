package com.meti.lib;

import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/24/2019
 */
public class TryableFactory<C extends Consumer<Exception>> {
    private final C callback;

    public TryableFactory(C callback) {
        this.callback = callback;
    }


}
