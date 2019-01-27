package com.meti.lib.bucket;

import com.meti.lib.util.CollectionUtil;

import java.util.*;
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

    public BucketManager() {
        this(null);
    }

    public BucketManager(Function<T, Bucket<T>> allocator) {
        this.allocator = allocator;
    }

    public void add(Bucket<T> bucket) {
        this.buckets.add(bucket);
    }

    public Map<T, Set<Bucket<T>>> handleAll(Collection<T> collection) {
        Map<T, Set<Bucket<T>>> map = new HashMap<>();
        for (T t : collection) {
            map.put(t, handle(t));
        }
        return map;
    }

    public Set<Bucket<T>> handle(T obj) {
        Set<Bucket<T>> collect = buckets.stream()
                .filter(tBucket -> tBucket.test(obj))
                .peek(tBucket -> tBucket.process(obj))
                .collect(Collectors.toSet());

        if (collect.isEmpty() && allocator != null) {
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

    public Bucket<T> searchForSingle(Object... parameters) {
        return CollectionUtil.toSingle(search(parameters));
    }

    public Set<Bucket<T>> search(Object... parameters) {
        return buckets.stream()
                .filter(tBucket -> tBucket.containsAllParameters(parameters))
                .collect(Collectors.toSet());
    }
}
