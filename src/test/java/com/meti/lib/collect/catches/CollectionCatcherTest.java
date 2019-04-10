package com.meti.lib.collect.catches;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CollectionCatcherTest {

    @Test
    void accept() {
        Exception exception = new Exception();
        CollectionCatcher<ArrayList<Exception>> catcher = new CollectionCatcher<>(new ArrayList<>());
        catcher.accept(exception);

        assertEquals(1, catcher.collection.size());
        assertTrue(catcher.collection.contains(exception));
    }
}