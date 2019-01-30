package com.meti.lib.bucket;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/30/2019
 */
class BucketTest {

    @Test
    void containsAllParameters() {
        assertTrue(new Bucket<>(new ParameterizedPredicate<>("test0", "test1", "test2") {
            @Override
            public boolean test(Object o) {
                return false;
            }
        }, new BufferedConsumer<>()).containsAllParameters("test0", "test1"));
    }

    @Test
    void containsParameter() {
        assertTrue(new Bucket<>(new ParameterizedPredicate<>("test0", "test1", "test2") {
            @Override
            public boolean test(Object o) {
                return false;
            }
        }, new BufferedConsumer<>()).containsParameter("test0"));
    }

    @Test
    void getContent() {
        BufferedConsumer<?> consumer = new BufferedConsumer<>("test0", "test1");
        Optional<Contentable<?, ?>> contentOptional = new Bucket<>(null, consumer).getContent();
        assertTrue(contentOptional.isPresent());

        Contentable<?, ?> contentable = contentOptional.get();
        Collection<?> content = contentable.getContent();

        assertEquals(2, content.size());
        assertTrue(content.contains("test0"));
        assertTrue(content.contains("test1"));
    }

    @Test
    void test() {
        assertTrue(new Bucket<>(new TypePredicate<>(String.class), null).test("test"));
    }

    @Test
    void process() {
        Bucket<Object> bucket = new Bucket<>(new TypePredicate<>(String.class), new BufferedConsumer<>());
        bucket.process("test");

        Optional<Contentable<?, ?>> contentOptional = bucket.getContent();
        assertTrue(contentOptional.isPresent());

        Contentable<?, ?> contentable = contentOptional.get();
        Object s = contentable.toSingle();

        assertEquals("test", s);
    }
}