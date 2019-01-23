package com.meti.lib.bucket;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TypePredicate<T> implements Parameterized<Class>, Predicate<Object> {
    private final Class<T> testClass;
    private final boolean useSubClass;

    public TypePredicate(Class<T> testClass) {
        this(testClass, true);
    }

    public TypePredicate(Class<T> testClass, boolean useSubClass) {
        this.testClass = testClass;
        this.useSubClass = useSubClass;
    }

    @Override
    public Set<Class> getParameters() {
        return Stream.of(testClass).collect(Collectors.toSet());
    }

    @Override
    public boolean test(Object o) {
        if (useSubClass) {
            return testClass.isAssignableFrom(o.getClass());
        } else {
            return testClass.equals(o.getClass());
        }
    }
}
