package com.meti.app;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/22/2019
 */
public class ApplicationLauncher {
    private final Method launchMethod;

    public ApplicationLauncher(Method launchMethod) {
        this.launchMethod = launchMethod;
    }

    public void launch(String[] args) throws Exception {
        checkLaunchMethod().invoke(null, (Object) args);
    }

    public Method checkLaunchMethod() {
        if (!Modifier.isStatic(launchMethod.getModifiers())) {
            throw new IllegalStateException(launchMethod + " is not static!");
        }
        return launchMethod;
    }
}
