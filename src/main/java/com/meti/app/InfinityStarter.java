package com.meti.app;

import com.meti.app.control.Menu;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;

class InfinityStarter {
    void startImpl(Stage primaryStage, InfinityState state) {
        try {
            setAndShowScene(primaryStage, new Scene(Menu.loadMenuParent(state)));
        } catch (IOException e) {
            state.getConsole().log(Level.SEVERE, e);
        }
    }

    void setAndShowScene(Stage primaryStage, Scene scene) {
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}