package com.meti.lib.module;

import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/7/2019
 */
public interface Module {
    <T> Set<T> getInstances(Class<T> instanceClass);
}
