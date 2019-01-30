package com.meti.lib.bucket;

import java.util.Collection;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public interface Parameterized<T> {
    Collection<T> getParameters();
}
