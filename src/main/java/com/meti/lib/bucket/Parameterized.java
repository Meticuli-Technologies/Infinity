package com.meti.lib.bucket;

import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public interface Parameterized<T> {
    Set<T> getParameters();
}
