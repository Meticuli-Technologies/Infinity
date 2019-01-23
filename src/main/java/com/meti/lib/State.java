package com.meti.lib;

public class State {
    private final BucketManager<Object> manager = new BucketManager<>(o -> new Bucket<>(new TypePredicate<>(o.getClass()), CollectionConsumer.empty()));
}
