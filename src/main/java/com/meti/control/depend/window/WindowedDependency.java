package com.meti.control.depend.window;

import com.meti.control.ControllerLoader;
import com.meti.control.depend.Dependency;
import com.meti.control.depend.DependencyManager;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/6/2018
 */
public class WindowedDependency extends Dependency {
    public static DependencyManager defaultDependencyManager;

    public final ObjectProperty<Stage> stageProperty = new SimpleObjectProperty<>();
    public final ObjectProperty<Scene> sceneProperty = new SimpleObjectProperty<>();

    public WindowedDependency(Stage stage, Scene scene) {
        this.stageProperty.set(stage);
        this.sceneProperty.set(scene);

        stage.sceneProperty().addListener((observable, oldValue, newValue) -> sceneProperty.set(newValue));
    }

    public <T> T onto(URL url) throws IOException {
        return onto(url, defaultDependencyManager);
    }

    public <T> T onto(URL url, DependencyManager dependencyManager) throws IOException {
        ControllerLoader loader = new ControllerLoader(url, dependencyManager);
        onto((Parent) loader.load());
        return loader.getController();
    }

    public Parent onto(Parent root){
        sceneProperty.get().setRoot(root);
        return root;
    }
}
