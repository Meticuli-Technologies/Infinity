package com.meti.lib.bucket;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public class BucketManager<T> {
    private final Set<Bucket<T>> buckets = new HashSet<>();
    private final Function<T, Bucket<T>> allocator;

    public BucketManager(Function<T, Bucket<T>> allocator) {
        this.allocator = allocator;
    }
}
