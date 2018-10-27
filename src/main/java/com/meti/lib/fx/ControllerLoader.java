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
        this(location, defaultDependencyManager);
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

    public <T> T loadWithDependencies() throws Exception {
        return loadWithDependencies(defaultControllerState);
    }

    public <T> T loadWithDependencies(ControllerState controllerState) throws Exception {
        T result = load();
        Object controllerToken = getController();

        loadController(controllerState, controllerToken);
        if(controllerState instanceof PostInitializable){
            ((PostInitializable) controllerState).postInitialize();
        }

        return result;
    }

    private Controller loadController(ControllerState controllerState, Object controllerToken) throws Exception {
        if (controllerToken instanceof Controller) {
            Controller controller = (Controller) controllerToken;

            Set<Class<? extends Dependency>> dependencyClasses = controller.getDependencyClasses();
            List<Exception> callback = new ArrayList<>();

            buildDependencies(controllerState, controller, dependencyClasses, callback);

            if (callback.size() > 0) {
                throw callback.get(0);
            }

            return controller;
        }
        else{
            throw new IllegalArgumentException("ControllerToken is not an instance of a Controller");
        }
    }

    private void buildDependencies(ControllerState controllerState, Controller controller, Set<Class<? extends Dependency>> dependencyClasses, List<Exception> callback) {
        dependencyClasses.stream()
                .map(aClass -> getDependencyClone(aClass, controllerState, callback))
                .filter(Objects::nonNull)
                .forEach(dependency -> controller.dependencies.put(dependency.getClass(), dependency));
    }

    private Dependency getDependencyClone(Class<? extends Dependency> aClass, ControllerState controllerState, List<Exception> callback) {
        try {
            Dependency clone = (Dependency) dependencyManager.getDependencyOfClass(aClass).clone();
            clone.load(controllerState);
            return clone;
        } catch (CloneNotSupportedException e) {
            callback.add(e);
            return null;
        }
    }
}
