package com.meti.lib.net;

import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/19/2019
 */
public class Query<R> implements Serializable {
    public final Class<R> returnType;

    public Query() {
        this(null);
    }

    public Query(Class<R> returnType) {
        this.returnType = returnType;
    }

    public boolean check(Class<?> other) {
        return returnType.equals(other);
    }
}
