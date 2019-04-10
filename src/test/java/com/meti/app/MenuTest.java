package com.meti.app;

import com.meti.lib.fx.FXUtil;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(ApplicationExtension.class)
class MenuTest {
    private Menu menu;

    @Test
    void local() {
        assertDoesNotThrow(() -> menu.local());
    }

    @Test
    void openSettings() {
        assertDoesNotThrow(() -> menu.openSettings());
    }

    @Test
    void getLocalURL(){
        assertNotNull(menu.getLocalURL());
    }

    @Test
    void loadLocal(){
        assertDoesNotThrow(() -> {
            Local local = FXUtil.call(menu::loadLocal).get();
            assertNotNull(local);
        });
    }

    @Start
    void primaryStage(Stage stage) throws IOException {
        Infinity infinity = new Infinity();
        infinity.start(stage);
        menu = infinity.loadMenu(stage);
    }
}