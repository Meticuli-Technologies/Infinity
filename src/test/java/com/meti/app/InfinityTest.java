package com.meti.app;

import com.meti.app.Infinity;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/13/2019
 */
@ExtendWith(ApplicationExtension.class)
class InfinityTest {
    private Infinity infinity = new Infinity();
    private Stage primaryStage;


    @Start
    void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Test
    void construct() {
        assertDoesNotThrow(() -> infinity.start(primaryStage));
    }

    @Test
    void init() {
        assertTrue(infinity.initializer.init());
    }

    //strings are converted into booleans implicitly
    @ParameterizedTest
    @ValueSource(strings = {"true", "false"})
    void startWithInitialized(boolean initialized) {
        Stage stage = Mockito.mock(Stage.class);
        TestInitializer initializer = new TestInitializer();
        infinity.initializer = initializer;
        infinity.initialized = initialized;

        infinity.start(stage);
        assertEquals(initialized, !initializer.called);
    }

    @Test
    void stop() {
        assertDoesNotThrow(() -> infinity.stop());
    }

    private class TestInitializer implements Infinity.InfinityInitializer {
        private boolean called;

        @Override
        public boolean init() {
            called = true;
            return true;
        }
    }
}