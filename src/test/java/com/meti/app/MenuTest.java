package com.meti.app;

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
        Infinity infinity = new Infinity();
        infinity.start(stage);
        menu = infinity.loadMenu(stage);
    }
}