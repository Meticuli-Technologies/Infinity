package com.meti;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/4/2019
 */
/*@ExtendWith(ApplicationExtension.class)*/
class MainTest {
    @Test
    void constructStatic() {
        assertNotNull(Main.infinity);
        assertNull(Main.instance);
    }

    @Test
    void main() {
        Main.infinity = new TestInfinity(true);
        Main.main(new String[0]);
        assertNotNull(Main.instance);
    }

    @Test
    void start() {
        TestInfinity infinity = new TestInfinity(false);
        Main.infinity = infinity;
        Main main = new Main();

        Stage mock = Mockito.mock(Stage.class);
        main.start(mock);
        assertEquals(mock, infinity.primaryStage);
        assertTrue(infinity.wasStarted);
        assertFalse(infinity.wasStopped);
    }

    @Test
    void stop() {
        TestInfinity infinity = new TestInfinity(false);
        Main.infinity = infinity;
        Main main = new Main();

        main.stop();
        assertNull(infinity.primaryStage);
        assertFalse(infinity.wasStarted);
        assertTrue(infinity.wasStopped);
    }

    private class TestInfinity extends Main.Infinity {
        private Stage primaryStage;
        private boolean wasStarted;
        private boolean wasStopped;

        private final boolean shouldExit;

        private TestInfinity(boolean shouldExit) {
            this.shouldExit = shouldExit;
        }

        @Override
        public void start(Stage primaryStage) {
            this.primaryStage = primaryStage;
            this.wasStarted = true;
            this.wasStopped = false;

            if (shouldExit) {
                Platform.exit();
            }
        }

        @Override
        public void stop() {
            this.primaryStage = null;
            this.wasStarted = false;
            this.wasStopped = true;
        }
    }
}
