package com.meti.lib.net;

import java.util.Arrays;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/19/2019
 */
public class Query {
    public final Class<?>[] classes;

    public Query(Class<?>[] classes) {
        this.classes = classes;
    }

    public boolean check(Class<?>[] others) {
        return Arrays.equals(classes, others);
    }
}
