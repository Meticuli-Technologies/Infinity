package com.meti.app;

import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
class InfinityTest {
    private Infinity infinity;

    @BeforeEach
    void beforeEach() {
        this.infinity = new Infinity();
    }

    @Test
    void start() {
        //Check start.
        assertDoesNotThrow(() -> infinity.start(Mockito.mock(Stage.class)));

        //If Infinity was already started, it shouldn't be able to be started again unless it has been stopped.
        assertThrows(IllegalStateException.class, () -> infinity.start(Mockito.mock(Stage.class)));

        //Cleanly stop Infinity.
        assertDoesNotThrow(() -> infinity.stop());
    }

    @Test
    void stop() {
        //Infinity hasn't been running, so it doesn't make sense to call stop when it's not running in the first place.
        assertThrows(IllegalStateException.class, () -> infinity.stop());

        //Check stop cleanly.
        assertDoesNotThrow(() -> infinity.start(Mockito.mock(Stage.class)));
        assertDoesNotThrow(() -> infinity.stop());
    }
}