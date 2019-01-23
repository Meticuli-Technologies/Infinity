package com.meti.lib.bucket;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BucketManager<T> {
    private final Set<Bucket<T>> buckets = new HashSet<>();
    private final Function<T, Bucket<T>> allocator;

    public BucketManager(Function<T, Bucket<T>> allocator) {
        this.allocator = allocator;
    }

    public Set<Bucket<T>> add(T t) {
        Set<Bucket<T>> added = buckets.stream().filter(tBucket -> tBucket.isValid(t))
                .peek(tBucket -> tBucket.handle(t))
                .collect(Collectors.toSet());

        if (added.isEmpty()) {
            Bucket<T> allocated = allocator.apply(t);
            buckets.add(allocated);
        }

        return added;
    }

    public Set<Bucket<T>> search(Object... parameters) {
        return buckets.stream().filter(tBucket -> tBucket.getParameters().containsAll(Arrays.asList(parameters)))
                .collect(Collectors.toSet());
    }
}
