package com.meti.lib.trys;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static com.meti.lib.util.CollectionUtil.toSingle;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/4/2019
 */
class TryableFactoryTest {
    private CollectionCatcher<ArrayList<Exception>> catcher;
    private TryableFactory<CollectionCatcher<ArrayList<Exception>>> factory;

    @BeforeEach
    void beforeEach() {
        this.catcher = new CollectionCatcher<>(new ArrayList<>());
        this.factory = new TryableFactory<>(catcher);
    }

    @Test
    void newConsumer() {
        UnsupportedOperationException e = new UnsupportedOperationException();
        factory.newConsumer(o -> {
            throw e;
        }).accept(null);

        Optional<Exception> optional = toSingle(catcher.collection);
        assertTrue(optional.isPresent());
        assertEquals(e, optional.get());
    }

    @Test
    void newFunction() {
        UnsupportedOperationException e = new UnsupportedOperationException();
        factory.newFunction(o -> {
            throw e;
        }).apply(null);

        Optional<Exception> optional = toSingle(catcher.collection);
        assertTrue(optional.isPresent());
        assertEquals(e, optional.get());
    }
}