package com.meti.app;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
class MainTest {
    @Test
    void mainNoException() throws Exception {
        Launcher launcher = Main.launcher;
        assertNotNull(launcher);
        assertEquals(InfinityLauncher.class, launcher.getClass());

        Main.launcher = new NoExceptionLauncher(NoExceptionLauncher.class.getMethod("test", Class.class, String[].class));
        Main.main(new String[]{"args"});
        assertEquals(Main.class, NoExceptionLauncher.clazz);
        assertArrayEquals(new String[]{"args"}, NoExceptionLauncher.args);

        Main.launcher = new InfinityLauncher();
    }

    @Test
    void mainWithException() throws Exception {
        Launcher launcher = Main.launcher;
        assertNotNull(launcher);
        assertEquals(InfinityLauncher.class, launcher.getClass());

        Main.launcher = new WithExceptionLauncher(WithExceptionLauncher.class.getMethod("test", Class.class, String[].class));
        assertThrows(InvocationTargetException.class, () -> Main.main(new String[0]));

        Main.launcher = new InfinityLauncher();
    }

    private static class NoExceptionLauncher extends Launcher {
        private static Class<?> clazz;
        private static String[] args;

        public NoExceptionLauncher(Method method) {
            super(method);
        }

        public static void test(Class<?> clazz, String[] args) {
            NoExceptionLauncher.clazz = clazz;
            NoExceptionLauncher.args = args;
        }
    }

    private static class WithExceptionLauncher extends Launcher {
        public WithExceptionLauncher(Method method) {
            super(method);
        }

        @SuppressWarnings("unused")
        public static void test(Class<?> clazz, String[] args) {
            throw new IllegalStateException();
        }
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
            throw new IllegalStateException();
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
