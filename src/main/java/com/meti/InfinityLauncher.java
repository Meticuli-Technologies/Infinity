package com.meti;

import javafx.application.Application;

import java.lang.reflect.Method;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/22/2019
 */
public class InfinityLauncher extends ApplicationLauncher {
    public InfinityLauncher() throws NoSuchMethodException {
        super(getApplicationLaunchMethod());
    }

    public static Method getApplicationLaunchMethod() throws NoSuchMethodException {
        return Application.class.getMethod("launch", String[].class);
    }
}
