package com.meti.lib.net;

import com.meti.lib.bucket.Bucket;
import com.meti.lib.bucket.BucketManager;
import com.meti.lib.bucket.TypePredicate;

import java.util.function.Consumer;

public class Input implements Consumer<Object> {
    private final BucketManager<Object> inputManager = new BucketManager<>();

    public <T> void addHandler(Class<T> clazz, Consumer<T> consumer) {
        Consumer<Object> castConsumer = o -> {
            if (clazz.isAssignableFrom(o.getClass())) {
                consumer.accept(clazz.cast(o));
            } else {
                throw new IllegalStateException(o + " was passed through the predicate but still does not qualify for acceptance");
            }
        };

        inputManager.add(new Bucket<>(new TypePredicate<>(clazz), castConsumer));
    }

    @Override
    public void accept(Object o) {
        inputManager.handle(o);
    }
}
