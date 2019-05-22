package com.meti.lib.javafx;

import com.meti.lib.reflect.Instantiator;
import javafx.util.Builder;
import javafx.util.BuilderFactory;
import javafx.util.Callback;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
class InjectorFactory implements BuilderFactory {
    private final List<Object> dependencies = new ArrayList<>();

    public InjectorFactory(Collection<Object> dependencies) {
        this.dependencies.addAll(dependencies);
    }

    @Override
    public Builder<?> getBuilder(Class<?> type) {
        return (Builder<Object>) () -> {
            try {
                return new Instantiator().instantiate(type, dependencies)
                        .stream()
                        .findAny()
                        .orElseThrow();
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                //TODO: internationalize
                throw new IllegalArgumentException("Unable to instantiate " + type, e);
            }
        };
    }
}
