package com.meti.lib.collect;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/17/2018
 */
public class ParameterizedMap<P, V> extends HashMap<Set<P>, V> {
    public void put(V value, P... parameters) {
        put(StreamUtil.asSet(parameters), value);
    }

    public Set<V> getFromParametersNonGeneric(Collection<?> parameters, Class<P> pClass){
        return getFromParameters(
                parameters.stream()
                        .filter((Predicate<Object>) o -> pClass.isAssignableFrom(o.getClass()))
                        .map((Function<Object, P>) pClass::cast)
                        .collect(Collectors.toSet())
        );
    }

    public Set<V> getFromParameters(Collection<P> parameters){
        return keySet().stream()
                .filter(ps -> ps.size() == parameters.size() && ps.containsAll(parameters))
                .map(this::get)
                .collect(Collectors.toSet());
    }
}
