package com.meti.lib.fx;

import javafx.fxml.FXMLLoader;
import javafx.util.Callback;

import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public class Injector extends FXMLLoader {
    public Injector(List<Object> dependencies) {
        this.setControllerFactory(new InjectorFactory(dependencies));
    }

    private class InjectorFactory implements Callback<Class<?>, Object> {
        private final List<Object> dependencies;

        public InjectorFactory(List<Object> dependencies) {
            this.dependencies = dependencies;
        }

        @Override
        public Object call(Class<?> param) {
            return null;
        }
    }
}
