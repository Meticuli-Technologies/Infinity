package com.meti.control;

import com.meti.control.depend.exception.ExceptionDependency;
import com.meti.control.depend.window.WindowedDependency;

import java.io.IOException;
import java.net.URL;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/7/2018
 */
public abstract class ContinuedController extends ResourcedController {
    @Override
    public String[] getDependencyNames() {
        return new String[]{
                "WindowedDependency",
                "ExceptionDependency"
        };
    }

    public <T> T load(URL resourceURL){
        try {
            return getDependency(WindowedDependency.class).onto(resourceURL);
        } catch (IOException e) {
            getDependency(ExceptionDependency.class).accept(e);
            return null;
        }
    }

    public <T> T load(int resourceIndex) {
       return load(resources[resourceIndex]);
    }
}
