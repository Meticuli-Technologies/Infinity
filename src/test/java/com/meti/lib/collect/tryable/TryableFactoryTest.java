package com.meti.lib.collect.tryable;

import com.meti.lib.collect.catches.Catcher;
import com.meti.lib.collect.catches.CollectionCatcher;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class TryableFactoryTest {
    @Test
    void construct() {
        Catcher catcher = Mockito.mock(Catcher.class);
        TryableFactory factory = new TryableFactory(catcher);
        assertEquals(catcher, factory.catcher);
    }

    @Test
    void performNotThrows() {
        CollectionCatcher<ArrayList<Exception>> catcher = new CollectionCatcher<>(new ArrayList<>());
        TryableFactory factory = new TryableFactory(catcher);
        Optional<Exception> optional = factory.perform(() -> {
        }).get();

        assertFalse(optional.isPresent());
    }

    @Test
    void performThrows() {
        CollectionCatcher<ArrayList<Exception>> catcher = new CollectionCatcher<>(new ArrayList<>());
        TryableFactory factory = new TryableFactory(catcher);

        Exception exception = new Exception();
        Optional<Exception> optional = factory.perform(() -> {
            throw exception;
        }).get();

        assertTrue(catcher.collection.contains(exception));
        assertTrue(optional.isPresent());
        assertEquals(exception, optional.get());
    }

    @Test
    void supplierNoException() {
        CollectionCatcher<ArrayList<Exception>> catcher = new CollectionCatcher<>(new ArrayList<>());
        TryableFactory factory = new TryableFactory(catcher);

        Object o = new Object();
        Supplier<Optional<Object>> supplier = factory.supplier(() -> o);

        Optional<Object> optional = supplier.get();
        assertTrue(optional.isPresent());
        assertEquals(o, optional.get());
    }

    @Test
    void supplierNull() {
        CollectionCatcher<ArrayList<Exception>> catcher = new CollectionCatcher<>(new ArrayList<>());
        TryableFactory factory = new TryableFactory(catcher);

        Supplier<Optional<Object>> supplier = factory.supplier(() -> null);
        Optional<Object> optional = supplier.get();
        assertFalse(optional.isPresent());
    }

    @Test
    void supplierWithException() {
        CollectionCatcher<ArrayList<Exception>> catcher = new CollectionCatcher<>(new ArrayList<>());
        TryableFactory factory = new TryableFactory(catcher);

        Supplier<Optional<Object>> supplier = factory.supplier(() -> {
            throw new IllegalStateException();
        });

        Optional<Object> optional = supplier.get();
        assertFalse(optional.isPresent());
    }
}