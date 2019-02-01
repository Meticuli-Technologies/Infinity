package com.meti.lib.bucket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/30/2019
 */
public abstract class ParameterizedPredicate<T, P> implements Parameterized<P>, Predicate<T> {
    protected final List<P> parameters = new ArrayList<>();

    @SafeVarargs
    public ParameterizedPredicate(P... parameters) {
        this.parameters.addAll(Arrays.asList(parameters));
    }

    @Override
    public Collection<P> getParameters() {
        return parameters;
    }
}
