package com.meti.lib.collect;

import com.meti.lib.convert.ClassConverter;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/19/2018
 */
public class ClassShelf extends Shelf<Object, ClassConverter<?>> {
    public ShelfBucket<Object, ClassConverter<?>> createBucket(Class<?> typeClass, boolean useSubClass) {
        return createBucket(new ClassConverter<>(typeClass, useSubClass));
    }
}
