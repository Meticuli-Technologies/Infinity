package com.meti.app;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
class InfinityTest {

    @Test
    void start() {
        assertDoesNotThrow(() -> new Infinity().start(Mockito.mock(Stage.class)));
    }

    @Test
    void stop() {
        assertDoesNotThrow(() -> new Infinity().stop());
    }
}