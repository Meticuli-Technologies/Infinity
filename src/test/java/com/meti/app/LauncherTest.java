package com.meti.app;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/7/2019
 */
class LauncherTest {

    @Test
    void invoke() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Launcher launcher = new Launcher(TestClass.class.getDeclaredMethod("confirm", Class.class, String[].class));
        launcher.invoke(TestClass.class, new String[]{"test"});

        assertEquals(TestClass.class, TestClass.clazz);
        assertArrayEquals(new String[]{"test"}, TestClass.args);
    }

    private static class TestClass {
        private static Class<?> clazz;
        private static String[] args;

        static void confirm(Class<?> clazz, String[] args){
            TestClass.clazz = clazz;
            TestClass.args = args;
        }
    }
}