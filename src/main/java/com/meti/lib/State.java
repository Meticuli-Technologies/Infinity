package com.meti.lib;

import com.meti.lib.bucket.Bucket;
import com.meti.lib.bucket.BucketManager;
import com.meti.lib.bucket.CollectionConsumer;
import com.meti.lib.bucket.TypePredicate;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class State {
    private final BucketManager<Object> manager = new BucketManager<>(o -> new Bucket<>(new TypePredicate<>(o.getClass()), CollectionConsumer.empty()));

    public <T> T storedElement(Class<T> tClass) {
        return CollectionUtil.toSingle(storedSet(tClass));
    }

    public <T> Set<T> storedSet(Class<T> tClass) {
        return manager.search(tClass)
                .stream()
                .flatMap((Function<Bucket<Object>, Stream<?>>) objectBucket -> objectBucket.getElements().stream())
                .filter(o -> tClass.isAssignableFrom(o.getClass()))
                .map(tClass::cast)
                .collect(Collectors.toSet());
    }
}
