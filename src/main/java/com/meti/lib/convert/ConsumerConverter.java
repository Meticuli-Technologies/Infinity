package com.meti.lib.convert;

import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/18/2018
 */
public abstract class ConsumerConverter<T> implements Consumer<Object> {
    private final Converter<T> tConverter;

    public ConsumerConverter(Converter<T> tConverter) {
        this.tConverter = tConverter;
    }

    @Override
    public void accept(Object o) {
        acceptT(tConverter.apply(o));
    }

    public abstract void acceptT(T object);
}
