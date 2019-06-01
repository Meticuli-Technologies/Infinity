package com.meti.lib.fx;

import com.meti.lib.reflect.Instantiator;
import javafx.fxml.FXMLLoader;
import javafx.util.Callback;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/1/2019
 */
public final class InjectorLoader extends FXMLLoader {
    public InjectorLoader(List<?> dependencies) {
        setControllerFactory(new InstantiatorCallback(dependencies));
    }

    private static class InstantiatorCallback implements Callback<Class<?>, Object> {
        private final List<?> dependencies;

        public InstantiatorCallback(List<?> dependencies) {
            this.dependencies = dependencies;
        }

        @Override
        public Object call(Class<?> param) {
            try {
                return Instantiator.genericInstanceToSingle(dependencies, param).orElseThrow();
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                throw new RuntimeException("Failed to instantiate controller: " + e);
            }
        }
    }
}
