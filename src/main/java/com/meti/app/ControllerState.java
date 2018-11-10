package com.meti.app;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class ControllerState extends HashSet<ControllerState.ControllerStateBindings<?>> {
    public static ControllerState of(Object... objects) {
        return new ControllerState().addAllObjects(objects);
    }

    public ControllerState addAllObjects(Object... objects) {
        Arrays.stream(objects).forEach(this::addObject);
        return this;
    }

    public <T> Optional<T> firstOfType(Class<T> tClass) {
        List<T> list = ofType(tClass);
        if (list.size() != 0) {
            return Optional.of(list.get(0));
        } else {
            return Optional.empty();
        }
    }

    public <T> List<T> ofType(Class<T> tClass) {
        List<ControllerStateBindings<?>> results = stream()
                .filter(controllerStateBindings -> tClass.equals(controllerStateBindings.tClass))
                .collect(Collectors.toList());

        if (results.size() == 0) {
            throw new IllegalArgumentException(tClass.getSimpleName() + " not found");
        } else {
            return results.stream()
                    .flatMap((Function<ControllerStateBindings<?>, Stream<?>>) controllerStateBindings -> controllerStateBindings.content.stream())
                    .filter(o -> o.getClass().isAssignableFrom(tClass))
                    .map(tClass::cast)
                    .collect(Collectors.toList());
        }
    }

    public ControllerState addObject(Object object) {
        Set<ControllerStateBindings<?>> results = stream()
                .filter(controllerStateBindings -> object.getClass().isAssignableFrom(controllerStateBindings.tClass))
                .peek(controllerStateBinding -> controllerStateBinding.add(object))
                .collect(Collectors.toSet());

        if (results.size() == 0) {
            add(new ControllerStateBindings<>(object.getClass(), object));
        }

        return this;
    }

    protected static class ControllerStateBindings<T> {
        private Class<T> tClass;
        private ArrayList<T> content = new ArrayList<>();

        public ControllerStateBindings(Class<T> initialClass, Object initial) {
            this(initialClass);
            add(initial);
        }

        public ControllerStateBindings(Class<T> tClass) {
            this.tClass = tClass;
        }

        private void add(Object object) {
            if (object.getClass().isAssignableFrom(tClass)) {
                content.add(tClass.cast(object));
            } else {
                throw new IllegalArgumentException("Invalid type " + object.getClass() + ", should be type " + tClass.getSimpleName());
            }
        }
    }
}
