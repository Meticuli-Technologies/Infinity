package com.meti.fx;

import com.meti.net.source.Source;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.util.Callback;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;

public class Injector extends FXMLLoader {
    private final List<Object> injectableList = new ArrayList<>();

    public Injector(Object... injectableArray) {
        this.injectableList.addAll(asList(injectableArray));
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

    private List<Class<?>> injectableClasses() {
        return injectableList.stream()
                .map(Object::getClass)
                .collect(Collectors.toList());
    }

    private class ControllerFactory implements Callback<Class<?>, Object> {
        @Override
        public Object call(Class<?> instantiatee) {
            try {
                return construct(instantiatee);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        private String buildConstructorString(Constructor<?> constructor) {
            return "Name: " + constructor.getName() + "\nParameters:\n\t" +
                    Arrays.stream(constructor.getParameters())
                            .map(Parameter::getType)
                            .map(Class::getSimpleName)
                            .collect(Collectors.joining("\n\t"));
        }

        private Object construct(Class<?> instantiatee) {
            Map<List<Class<?>>, Constructor<?>> constructorMap = buildConstructorMap(instantiatee);
            List<Constructor<?>> validConstructors = findValidConstructors(constructorMap);
            if (validConstructors.isEmpty())
                throw new IllegalStateException("No valid constructors found, these constructors were found:\n" + buildConstructorsString(constructorMap.values()));
            if (validConstructors.size() > 1)
                throw new IllegalStateException("Too many valid constructors:\n" + buildConstructorsString(validConstructors));
            return validConstructors.get(0);
        }

        private String buildConstructorsString(Collection<Constructor<?>> values) {
            return values.stream().map(this::buildConstructorString).collect(Collectors.joining("\n"));
        }

        private Map<List<Class<?>>, Constructor<?>> buildConstructorMap(Class<?> instantiatee) {
            Map<List<Class<?>>, Constructor<?>> constructorMap = new HashMap<>();
            for (Constructor<?> constructor : instantiatee.getConstructors()) {
                constructorMap.put(getTypes(constructor.getParameters()), constructor);
            }
            return constructorMap;
        }

        private List<Constructor<?>> findValidConstructors(Map<List<Class<?>>, Constructor<?>> constructorMap) {
            List<Class<?>> injectableClasses = injectableClasses();
            return constructorMap.keySet().stream()
                    .filter(parameterClasses -> areInstances(injectableClasses, parameterClasses))
                    .map(constructorMap::get)
                    .collect(Collectors.toList());
        }

        private List<Class<?>> getTypes(Parameter[] parameters) {
            return Arrays.stream(parameters)
                    .map(Parameter::getType)
                    .collect(Collectors.toList());
        }

        private boolean areInstances(List<Class<?>> checked, List<Class<?>> checking) {
            if (checked.size() != checking.size()) {
                return false;
            } else return IntStream.range(0, checked.size()).allMatch(checkedIndex -> isInstance(
                    checked.get(checkedIndex),
                    checking.get(checkedIndex)
            ));
        }

        private boolean isInstance(Class<?> checked, Class<?> checking) {
            return checked.isAssignableFrom(checking);
        }
    }
}
