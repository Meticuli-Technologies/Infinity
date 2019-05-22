package com.meti.app.control;

import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/22/2019
 */
public interface Editor {
    boolean canShow(Class<?> clazz);

    default boolean canShowAll(Set<Class<?>> assetClasses) {
        return assetClasses.stream().allMatch(aClass -> canShowAll(assetClasses));
    }

    String getName();
}
