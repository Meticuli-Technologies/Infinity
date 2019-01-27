package com.meti.lib.state;

import com.meti.lib.bucket.Bucket;
import com.meti.lib.bucket.BucketManager;
import com.meti.lib.bucket.BufferedConsumer;
import com.meti.lib.bucket.TypePredicate;

import java.util.Arrays;
import java.util.function.Function;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public class State {
    public final BucketManager<Object> buckets = new BucketManager<>(new BucketAllocator());

    public State(Object... objects){
        buckets.addAll(Arrays.asList(objects));
    }

    private static class BucketAllocator implements Function<Object, Bucket<Object>> {
        @Override
        public Bucket<Object> apply(Object o) {
            return new Bucket<>(new TypePredicate<>(o.getClass()), new BufferedConsumer<>());
        }
    }
}
