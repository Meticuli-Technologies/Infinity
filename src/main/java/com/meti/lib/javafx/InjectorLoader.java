package com.meti.lib.javafx;

import com.meti.lib.reflect.Instantiator;
import javafx.fxml.FXMLLoader;
import javafx.util.Callback;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/1/2019
 */
public final class InjectorLoader extends FXMLLoader {
    private InjectorLoader(List<?> dependencies) {
        setControllerFactory(new InstantiatorCallback(dependencies));
    }

    public static <T> T load(List<?> dependencies, URL url) throws IOException {
        return load(dependencies, url.openStream());
    }

    private static <T> T load(List<?> dependencies, InputStream inputStream) throws IOException {
        return new InjectorLoader(dependencies).load(inputStream);
    }

    private static class InstantiatorCallback implements Callback<Class<?>, Object> {
        private final List<?> dependencies;

        InstantiatorCallback(List<?> dependencies) {
            this.dependencies = dependencies;
        }

        @Override
        public Object call(Class<?> param) {
            try {
                return Instantiator.genericInstanceToSingle(dependencies, param).orElseThrow();
            } catch (Exception e) {
                throw new RuntimeException("Failed to instantiate controller: " + e);
            }
        }
    }
}
