package com.meti.lib.util;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXUtil {
    public static Stage newStageFrom(Parent root) {
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        return stage;
    }
}
