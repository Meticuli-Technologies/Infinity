package com.meti.lib.fx;

import javafx.fxml.FXMLLoader;

import java.net.URL;
import java.util.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/23/2018
 */
public class ControllerLoader extends FXMLLoader {
    public static DependencyManager defaultDependencyManager;
    public static ControllerState defaultControllerState;

    public final DependencyManager dependencyManager;

    public ControllerLoader(URL location) {
        this(location, new DependencyManager());
    }

    public ControllerLoader(URL location, DependencyManager dependencyManager) {
        super(location);
        this.dependencyManager = dependencyManager;
    }

    public static <T> T loadWithDependenciesStatic(URL url) throws Exception {
        return loadWithDependenciesStatic(url, defaultControllerState);
    }

    public static <T> T loadWithDependenciesStatic(URL url, ControllerState controllerState) throws Exception {
        Objects.requireNonNull(defaultDependencyManager);
        return loadWithDependenciesStatic(url, defaultDependencyManager, controllerState);
    }

    public static <T> T loadWithDependenciesStatic(URL url, DependencyManager dependencyManager) throws Exception {
        return loadWithDependenciesStatic(url, dependencyManager, defaultControllerState);
    }

    public static <T> T loadWithDependenciesStatic(URL url, DependencyManager dependencyManager, ControllerState controllerState) throws Exception {
        return new ControllerLoader(url, dependencyManager).loadWithDependencies(controllerState);
    }

    public <T> T loadWithDependencies(ControllerState controllerState) throws Exception {
        T result = load();
        Object controllerToken = getController();

        if (controllerToken instanceof Controller) {
            Controller controller = (Controller) controllerToken;
            Set<Class<? extends Dependency>> classes = controller.getDependencyClasses();

            List<Exception> callback = new ArrayList<>();
            classes.stream()
                    .map(aClass -> {
                        try {
                            Dependency clone = (Dependency) dependencyManager.getDependencyOfClass(aClass).clone();
                            clone.load(controllerState);
                            return clone;
                        } catch (CloneNotSupportedException e) {
                            callback.add(e);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .forEach(dependency -> controller.dependencies.put(dependency.getClass(), dependency));
            if (callback.size() > 0) {
                throw callback.get(0);
            }
        }

        return result;
    }
}
