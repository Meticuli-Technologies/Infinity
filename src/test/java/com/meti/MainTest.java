package com.meti;

import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/13/2019
 */
class MainTest {
    private TestInfinityContext context;
    private Main main;

    @BeforeEach
    void beforeEach() {
        context = new TestInfinityContext();
        main = new Main(context);
    }

    @Test
    void construct(){
        Main main = new Main();
        InfinityContext context = main.context;
        assertEquals(Infinity.class, context.getClass());
    }

    @Test
    void main() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            Main.main(new String[]{});
            assertTrue(Main.launched);
        });
    }

    @Test
    void start() {
        Stage stage = Mockito.mock(Stage.class);
        main.start(stage);

        assertEquals(stage, context.primaryStage);
        assertTrue(context.started);
        assertFalse(context.stopped);
    }

    @Test
    void stop() {
        main.stop();

        assertFalse(context.started);
        assertTrue(context.stopped);
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