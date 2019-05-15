package com.meti.fx;

import com.meti.net.source.URLSource;
import javafx.fxml.FXMLLoader;
import javafx.util.Callback;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;

public class Injector extends FXMLLoader {
    private final List<Object> injectableList = new ArrayList<>();

    public Injector(URLSource source, Object... injectableArray) {
        super(source.getURL());
        this.injectableList.addAll(asList(injectableArray));
        setControllerFactory(new ControllerFactory());
    }

    private List<Class<?>> injectableClasses() {
        return injectableList.stream()
                .map(Object::getClass)
                .collect(Collectors.toList());
    }

    private class ControllerFactory implements Callback<Class<?>, Object> {
        private String buildConstructorString(Constructor<?> constructor) {
            return "Name: " + constructor.getName() + "\nParameters:\n\t" +
                    Arrays.stream(constructor.getParameters())
                            .map(Parameter::getType)
                            .map(Class::toString)
                            .collect(Collectors.joining("\n\t"));
        }

        @Override
        public Object call(Class<?> instantiatee) {
            try {
                return checkInstance(construct(instantiatee), instantiatee);
            } catch (Exception e) {
                throw new RuntimeException("Failed to instantiate " + instantiatee, e);
            }
        }

        private Object checkInstance(Object token, Class<?> tokenClass){
            if(!tokenClass.isInstance(token)) throw new IllegalArgumentException("Object of type " + token.getClass() + " does not conform to " + tokenClass);
            return token;
        }

        private Object construct(Class<?> instantiatee) throws IllegalAccessException, InvocationTargetException, InstantiationException {
            Map<List<Class<?>>, Constructor<?>> constructorMap = buildConstructorMap(instantiatee);
            List<Constructor<?>> validConstructors = findValidConstructors(constructorMap);
            checkValidConstructors(constructorMap, validConstructors);
            return validConstructors.get(0).newInstance(injectableList.toArray());
        }

        private void checkValidConstructors(Map<List<Class<?>>, Constructor<?>> constructorMap, List<Constructor<?>> validConstructors) {
            if (validConstructors.isEmpty())
                throw new IllegalStateException("No valid constructors found for parameters:\n\t" + injectableClasses().stream().map(Objects::toString).collect(Collectors.joining("\n\t")) +
                        "\nThese constructors were found:\n" + buildConstructorsString(constructorMap.values()));
            if (validConstructors.size() > 1)
                throw new IllegalStateException("Too many valid constructors:\n" + buildConstructorsString(validConstructors));
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

        private List<Class<?>> getTypes(Parameter[] parameters) {
            return Arrays.stream(parameters)
                    .map(Parameter::getType)
                    .collect(Collectors.toList());
        }

        private List<Constructor<?>> findValidConstructors(Map<List<Class<?>>, Constructor<?>> constructorMap) {
            List<Class<?>> injectableClasses = injectableClasses();
            return constructorMap.keySet().stream()
                    .filter(parameterClasses -> areInstances(injectableClasses, parameterClasses))
                    .map(constructorMap::get)
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
