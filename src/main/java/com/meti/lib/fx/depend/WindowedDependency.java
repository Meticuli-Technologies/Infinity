package com.meti.lib.fx.depend;

import com.meti.lib.fx.ControllerState;
import com.meti.lib.fx.Dependency;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/23/2018
 */
public class WindowedDependency extends Dependency {
    public Stage stage;
    public Scene scene;

    @Override
    public void load(ControllerState controllerState) {
        stage = controllerState.getObject(Stage.class);
    }
}
