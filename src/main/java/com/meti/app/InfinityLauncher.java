package com.meti.app;

import javafx.application.Application;

import java.lang.reflect.Method;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/7/2019
 */
class InfinityLauncher extends Launcher {
    public InfinityLauncher() throws NoSuchMethodException {
        super(getLaunchMethod());
    }

    public static Method getLaunchMethod() throws NoSuchMethodException {
        return Application.class.getMethod("launch", Class.class, String[].class);
    }
}
