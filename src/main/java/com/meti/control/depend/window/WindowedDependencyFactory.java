package com.meti.control.depend.window;

import com.meti.control.depend.Dependency;
import com.meti.control.depend.DependencyFactory;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/6/2018
 */
public class WindowedDependencyFactory extends DependencyFactory {
    public Stage stage;
    public Scene scene;

    public WindowedDependencyFactory(Stage stage) {
        this();
        this.stage = stage;
    }

    public WindowedDependencyFactory(Stage stage, Scene scene) {
        this();
        this.stage = stage;
        this.scene = scene;
    }

    public WindowedDependencyFactory() {
        super("WindowedDependency");
    }

    @Override
    public Dependency create() {
        return new WindowedDependency(stage, scene);
    }
}
