package com.meti.app.core;

import com.meti.app.ApplicationLauncher;
import javafx.application.Application;

import java.lang.reflect.Method;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/22/2019
 */
class InfinityLauncher extends ApplicationLauncher {
    InfinityLauncher() throws NoSuchMethodException {
        super(getApplicationLaunchMethod());
    }

    private static Method getApplicationLaunchMethod() throws NoSuchMethodException {
        return Application.class.getMethod("launch", Class.class, String[].class);
    }
}
