package com.meti;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/13/2019
 */
class MainTest {
    private Main main;

    @BeforeEach
    void beforeEach() {
        main = new Main();
    }

    @AfterEach
    void afterEach() {
        Main.context = new Infinity();
    }

    @Test
    void construct(){
        InfinityContext context = Main.context;
        assertEquals(Infinity.class, context.getClass());
    }

    @Test
    void main() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            Main.context = new InstantContext();
            Main.main(new String[]{});
            assertTrue(Main.launched);
        });
    }

    @Test
    void start() {
        TestInfinityContext context = new TestInfinityContext();
        Main.context = context;

        Stage stage = Mockito.mock(Stage.class);
        main.start(stage);

        assertEquals(stage, context.primaryStage);
        assertTrue(context.started);
        assertFalse(context.stopped);
    }

    @Test
    void stop() {
        TestInfinityContext context = new TestInfinityContext();
        Main.context = context;

        main.stop();

        assertFalse(context.started);
        assertTrue(context.stopped);
    }

    private static class InstantContext implements InfinityContext {
        @Override
        public void start(Stage primaryStage) {
            Platform.exit();
        }

        @Override
        public void stop() {

        }
    }
    private class TestInfinityContext implements InfinityContext {
        private boolean started = false;
        private boolean stopped = false;
        private Stage primaryStage;

        @Override
        public void start(Stage primaryStage) {
            this.primaryStage = primaryStage;
            started = true;
        }

        @Override
        public void stop() {
            stopped = true;
        }
    }
}