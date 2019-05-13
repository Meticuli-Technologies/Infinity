package com.meti.fx;

import com.meti.net.source.Source;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.util.Callback;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Injector extends FXMLLoader {
    private final List<Object> injectableList = new ArrayList<>();

    public Injector(Object... injectableArray) {
        this.injectableList.addAll(Arrays.asList(injectableArray));
        setControllerFactory(new ControllerFactory());
    }

    public static Scene loadAsScene(Source source, Object... injectableArray) throws IOException {
        return new Scene(loadStatic(source, injectableArray));
    }

    public static <T> T loadStatic(Source source, Object... injectableArray) throws IOException {
        return new Injector(injectableArray).load(source);
    }

    public <T> T load(Source source) throws IOException {
        return load(source.getInputStream());
    }

    private class ControllerFactory implements Callback<Class<?>, Object> {
        @Override
        public Object call(Class<?> instantiatee) {
            try {
                List<Class<?>> dependencyClasses = getClassArray(injectableList);
                Map<Parameter[], Constructor<?>> constructorMap = buildConstructorMap(instantiatee);
                Optional<Constructor<?>> constructor = matchConstructor(dependencyClasses, constructorMap);
                if (!constructor.isPresent()) {
                    throw new NoSuchMethodException();
                }

                return constructor.get().newInstance(injectableList.toArray());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        private Optional<Constructor<?>> matchConstructor(List<Class<?>> classes, Map<Parameter[], Constructor<?>> constructorMap) {
            return constructorMap.keySet()
                    .stream()
                    .filter(parameters -> testParameters(parameters, classes))
                    .map((Function<Parameter[], Constructor<?>>) constructorMap::get)
                    .findAny();
        }

        private boolean testParameters(Parameter[] parameters, List<Class<?>> classes) {
            Class<?>[] parameterClasses = Arrays.stream(parameters)
                    .map(Parameter::getType)
                    .toArray(Class[]::new);
/*
            if (parameterClasses.length != classes.length) {
                return false;
            }

            boolean allImplementations = true;
            for (int i = 0; i < parameterClasses.length; i++) {
                if (!parameterClasses[i].isAssignableFrom(classes[i])) {
                    allImplementations = false;
                }
            }*/

            return allImplementations;
        }

        private Map<Parameter[], Constructor<?>> buildConstructorMap(Class<?> instantiatee) {
            List<Constructor<?>> constructors = Arrays.asList(instantiatee.getDeclaredConstructors());
            return constructors.stream()
                    .collect(Collectors.toMap(Executable::getParameters, Function.identity()));
        }

        private List<Class<?>> getClassArray(List<Object> objects) {
            return objects.stream()
                    .map(Object::getClass)
                    .collect(Collectors.toList());
        }
    }
}
