package com.meti.app;

import com.meti.lib.collect.State;
import com.meti.lib.collect.tryable.TryableFactory;
import com.meti.lib.log.LoggerConsole;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InfinityControllerTest {
    @Test
    void construct() {
        LoggerConsole console = Mockito.mock(LoggerConsole.class);
        TryableFactory factory = Mockito.mock(TryableFactory.class);
        InfinityController controller = new InfinityController(new State(console, factory));
        assertEquals(console, controller.console);
        assertEquals(factory, controller.factory);
    }

    @Test
    void constructEmptyState() {
        assertThrows(NoSuchElementException.class, () -> new InfinityController(new State()));
    }
}