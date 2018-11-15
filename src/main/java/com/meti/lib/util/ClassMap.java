package com.meti.lib.util;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class ClassMap extends HashSet<MapBinding<?>> {
    public ClassMap() {
    }

    public ClassMap(Object... objects){
        addAllObjects(objects);
    }

    public ClassMap addAllObjects(Object... objects) {
        Arrays.stream(objects).forEach(this::addObject);
        return this;
    }

    public <T> Optional<T> firstOfType(Class<T> tClass) {
        List<T> list = ofType(tClass);
        if (list != null && list.size() != 0) {
            return Optional.of(list.get(0));
        } else {
            return Optional.empty();
        }
    }

    public <T> List<T> ofType(Class<T> tClass) {
        List<MapBinding<?>> results = stream()
                .filter(controllerStateBindings -> tClass.isAssignableFrom(controllerStateBindings.tClass))
                .collect(Collectors.toList());

        if (results.size() == 0) {
            return new ArrayList<>();
        } else {
            return results.stream()
                    .flatMap((Function<MapBinding<?>, Stream<?>>) controllerStateBindings -> controllerStateBindings.content.stream())
                    .filter(o -> o.getClass().isAssignableFrom(tClass))
                    .map(tClass::cast)
                    .collect(Collectors.toList());
        }
    }

    public ClassMap addObject(Object object) {
        Set<MapBinding<?>> results = stream()
                .filter(controllerStateBindings -> object.getClass().isAssignableFrom(controllerStateBindings.tClass))
                .peek(controllerStateBinding -> controllerStateBinding.add(object))
                .collect(Collectors.toSet());

        if (results.size() == 0) {
            add(new MapBinding<>(object.getClass(), object));
        }

        return this;
    }

}
