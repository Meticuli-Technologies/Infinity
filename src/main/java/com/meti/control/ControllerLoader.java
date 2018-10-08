package com.meti.control;

import com.meti.control.depend.Dependency;
import com.meti.control.depend.DependencyManager;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/6/2018
 */
public class ControllerLoader extends FXMLLoader {
    private final DependencyManager dependencyManager;

    public ControllerLoader(URL location, DependencyManager dependencyManager) {
        super(location);
        this.dependencyManager = dependencyManager;
    }

    @Override
    public <T> T load() throws IOException {
        T result = super.load();

        Object controllerObject = getController();
        if (controllerObject instanceof Controller) {
            Controller controller = (Controller) controllerObject;

            Arrays.stream(controller.getDependencyNames())
                    .map(s -> dependencyManager.getOrDefault(s, dependencyManager.get("BlankDependency")))
                    .forEach(dependencyFactory -> {
                        Dependency dependency = dependencyFactory.create();
                        controller.put(dependency.name, dependency);
                    });
        }

        if(controllerObject instanceof PostInitializable){
            ((PostInitializable) controllerObject).postInitialize();
        }

        return result;
    }

    public static <T> T load(URL url, DependencyManager dependencyManager) throws IOException {
        return new ControllerLoader(url, dependencyManager).load();
    }
}
