package com.meti.app.core;

import com.meti.app.control.Menu;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

class InfinityStarter {
    void startImpl(Stage primaryStage, InfinityState state) throws IOException {
        setAndShowScene(primaryStage, new Scene(Menu.loadMenuParent(state)));
    }

    private void setAndShowScene(Stage primaryStage, Scene scene) {
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}