package com.meti.lib.fx.depend;

import com.meti.lib.fx.ControllerState;
import com.meti.lib.fx.Dependency;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/23/2018
 */
public class WindowedDependency extends Dependency {
    public final ObjectProperty<Stage> stageProperty = new SimpleObjectProperty<>();
    public final ObjectProperty<Scene> sceneProperty = new SimpleObjectProperty<>();

    public WindowedDependency(Stage stage, Scene scene) {
        stageProperty.set(stage);
        sceneProperty.set(scene);
    }

    @Override
    public void load(ControllerState controllerState) {
        stageProperty.set(controllerState.getObject(Stage.class));
    }
}
