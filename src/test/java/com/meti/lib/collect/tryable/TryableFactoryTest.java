package com.meti.lib.collect.tryable;

import com.meti.lib.collect.catches.Catcher;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TryableFactoryTest {
    @Test
    void construct() {
        Catcher catcher = Mockito.mock(Catcher.class);
        TryableFactory factory = new TryableFactory(catcher);
        assertEquals(catcher, factory.catcher);
    }
}