package com.meti.app;

import javafx.application.Application;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/7/2019
 */
class InfinityLauncherTest {

    @Test
    void getLaunchMethod() throws NoSuchMethodException {
        Method method = InfinityLauncher.getLaunchMethod();
        assertNotNull(method);
        assertEquals(Application.class.getMethod("launch", Class.class, String[].class), method);
    }
}