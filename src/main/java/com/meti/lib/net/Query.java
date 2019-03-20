package com.meti.lib.net;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/19/2019
 */
public class Query<R> {
    public final Class<R> returnType;

    public Query(Class<R> returnType) {
        this.returnType = returnType;
    }

    public boolean check(Class<?> other) {
        return returnType.equals(other);
    }
}
