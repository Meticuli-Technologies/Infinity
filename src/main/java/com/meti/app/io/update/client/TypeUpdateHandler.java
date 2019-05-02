package com.meti.app.io.update.client;

import com.meti.app.io.update.Update;
import com.meti.lib.util.collect.TypeFunction;
import com.meti.lib.util.collect.TypePredicate;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/2/2019
 */
public abstract class TypeUpdateHandler<T extends Update> implements UpdateHandler {
    private final TypePredicate<T> predicate;
    private final TypeFunction<T> function;

    public TypeUpdateHandler(Class<T> tClass) {
        this.predicate = new TypePredicate<>(tClass);
        this.function = new TypeFunction<>(tClass);
    }

    @Override
    public void accept(Update update) {
        handle(function.apply(update));
    }

    public abstract void handle(T update);

    @Override
    public boolean test(Update update) {
        return predicate.test(update);
    }
}
