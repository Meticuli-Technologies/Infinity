package com.meti.lib.state;

import com.meti.lib.bucket.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public class State {
    public final BucketManager<Object> buckets = new BucketManager<>(new BucketAllocator());

    public State(Object... objects){
        buckets.handleAll(Arrays.asList(objects));
    }

    public <T> T singleContent(Class<T> typeClass) {
        Optional<Contentable<?, ?>> content = buckets.searchForSingle(typeClass).getContent();
        if (!content.isPresent()) {
            throw new IllegalStateException(buckets + " does not contain instances of " + Contentable.class);
        }

        return typeClass.cast(content.get().toSingle());
    }

    public <T> List<T> multipleContent(Class<T> typeClass) {
        return buckets.search(typeClass)
                .stream()
                .map(Bucket::getContent)
                .flatMap(Optional::stream)
                .flatMap(contentable -> contentable.getContent().stream())
                .filter(o -> typeClass.isAssignableFrom(o.getClass()))
                .map(typeClass::cast)
                .collect(Collectors.toList());
    }

    private static class BucketAllocator implements Function<Object, Bucket<Object>> {
        @Override
        public Bucket<Object> apply(Object o) {
            return new Bucket<>(new TypePredicate<>(o.getClass()), new BufferedConsumer<>());
        }
    }
}
