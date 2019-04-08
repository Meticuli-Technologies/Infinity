package com.meti.app;

import com.meti.lib.collect.State;
import com.meti.lib.fx.ControllerLoader;
import com.meti.lib.fx.FXMLBundle;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

@ExtendWith(ApplicationExtension.class)
class MenuTest {
    private Menu menu;

    @BeforeEach
    void beforeEach() {

    }

    @Test
    void local() {

    }

    @Test
    void openSettings() {
    }

    @Start
    void primaryStage(Stage stage) throws IOException {
        State state = new State();
        ControllerLoader loader = new ControllerLoader(state);
        FXMLBundle<?> bundle = loader.getBundle(Infinity.getMenuURL().openStream());
        menu = (Menu) bundle.controller;
        stage.setScene(new Scene(bundle.parent));
        stage.show();
    }
}