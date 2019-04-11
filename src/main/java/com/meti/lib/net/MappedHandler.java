package com.meti.lib.net;

import com.meti.lib.util.CollectionUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
class MappedHandler extends HashSet<Handler<?>> implements Handler<Object> {
    @Override
    public Object apply(Object token) {
        Set<Object> results =
                stream()
                .filter(handler -> handler.test(token))
                .map(handler -> handler.apply(token))
                .collect(Collectors.toSet());

        try {
            return CollectionUtil.toSingle(results);
        } catch (Exception e) {
            return e;
        }
    }

    @Override
    public boolean test(Object o) {
        return true;
    }
}
