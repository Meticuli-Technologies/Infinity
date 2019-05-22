package com.meti.lib.javafx;

import com.meti.lib.reflect.Instantiator;
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
class InjectorFactory implements Callback<Class<?>, Object> {
    private final List<Object> dependencies = new ArrayList<>();

    public InjectorFactory(Collection<Object> dependencies) {
        this.dependencies.addAll(dependencies);
    }

    @Override
    public Object call(Class<?> param) {
        try {
            return new Instantiator().instantiate(param, dependencies)
                    .stream()
                    .findAny()
                    .orElseThrow();
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            //TODO: internationalize
            throw new IllegalArgumentException("Unable to instantiate " + param, e);
        }
    }
}
