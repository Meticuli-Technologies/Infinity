package com.meti.lib.fx;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/27/2018
 */
public class FXUtil {
    private FXUtil() {
    }

    public static Stage onto(Parent parent) {
        return onto(parent, new Stage());
    }

    public static Stage onto(Parent parent, Stage stage) {
        stage.setScene(new Scene(parent));
        stage.show();

        return stage;
    }
}
