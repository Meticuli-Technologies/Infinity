package com.meti.control;

import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/14/2019
 */
public interface Constructable<T> {
    void setOnConstructed(Consumer<T> consumer);
}
