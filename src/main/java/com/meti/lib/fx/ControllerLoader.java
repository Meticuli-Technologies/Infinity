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
    public final DependencyManager dependencyManager;

    public ControllerLoader(URL location) {
        this(location, new DependencyManager());
    }

    public ControllerLoader(URL location, DependencyManager dependencyManager) {
        super(location);
        this.dependencyManager = dependencyManager;
    }

    public static <T> T loadWithDependenciesStatic(URL url) throws Exception {
        Objects.requireNonNull(defaultDependencyManager);
        return loadWithDependenciesStatic(url, defaultDependencyManager);
    }

    public static <T> T loadWithDependenciesStatic(URL url, DependencyManager dependencyManager) throws Exception {
        return new ControllerLoader(url, dependencyManager).loadWithDependencies();
    }

    public <T> T loadWithDependencies() throws Exception {
        T result = load();
        Object controllerToken = getController();

        if (controllerToken instanceof Controller) {
            Controller controller = (Controller) controllerToken;
            Set<Class<? extends Dependency>> classes = controller.getDependencyClasses();

            List<Exception> callback = new ArrayList<>();
            classes.stream()
                    .map(aClass -> {
                        try {
                            return (Dependency) dependencyManager.getDependencyOfClass(aClass).clone();
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
