package com.meti.lib.bucket;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public Set<Bucket<T>> add(T obj) {
        Set<Bucket<T>> collect = buckets.stream()
                .filter(tBucket -> tBucket.test(obj))
                .peek(tBucket -> tBucket.process(obj))
                .collect(Collectors.toSet());

        if (collect.isEmpty()) {
            Bucket<T> applied = allocator.apply(obj);
            if (!applied.test(obj)) {
                throw new IllegalStateException(applied + " returned by allocator does not accept " + obj);
            }

            applied.process(obj);
            buckets.add(applied);
            return Collections.singleton(applied);
        } else {
            return collect;
        }
    }
}
