package com.meti.app;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
class MainTest {
    @Test
    void main() {
        Main.implementation = new ExitImpl();
        assertTimeout(Duration.ofSeconds(1), () -> Main.main(new String[0]));
    }

    @Test
    void start(){
        TestImpl implementation = new TestImpl();
        Main.implementation = implementation;

        Stage mock = Mockito.mock(Stage.class);
        new Main().start(mock);

        assertNotNull(Main.instance);
        assertEquals(mock, implementation.primaryStage);
        assertTrue(implementation.wasStarted);
        assertFalse(implementation.wasStopped);
    }

    @Test
    void stop(){
        TestImpl implementation = new TestImpl();
        Main.implementation = implementation;
        new Main().stop();

        assertNull(Main.instance);
        assertNull(implementation.primaryStage);
        assertFalse(implementation.wasStarted);
        assertTrue(implementation.wasStopped);
    }

    private class ExitImpl implements InfinityImpl {
        @Override
        public void start(Stage primaryStage) {
            Platform.exit();
        }

        @Override
        public void stop() {
        }
    }

    private class TestImpl implements InfinityImpl {
        private Stage primaryStage;
        private boolean wasStarted;
        private boolean wasStopped;

        @Override
        public void start(Stage primaryStage) {
            this.primaryStage = primaryStage;
            this.wasStarted = true;
            this.wasStopped = false;
        }

        @Override
        public void stop() {
            this.primaryStage = null;
            this.wasStarted = false;
            this.wasStopped = true;
        }
    }
}
