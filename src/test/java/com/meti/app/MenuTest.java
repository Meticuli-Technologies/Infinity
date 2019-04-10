package com.meti.app;

import com.meti.lib.fx.FXMLBundle;
import com.meti.lib.fx.FXUtil;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class MenuTest {
    private Menu menu;

    @Test
    void getLocalURL() {
        assertNotNull(URLS.getLocalURL());
    }

    @Test
    void loadLocal() {
        assertDoesNotThrow(() -> {
            FXMLBundle<Local> local = FXUtil.call(menu::loadLocal).get();
            assertNotNull(local.controller);
            assertEquals(local.parent, menu.stage.getScene().getRoot());
        });
    }

    @Test
    void local() throws ExecutionException, InterruptedException {
        assertFalse(FXUtil.call(menu::local).get().isPresent());
    }

    @Test
    void openSettings() {
        assertDoesNotThrow(() -> menu.openSettings());
    }

    @Start
    void primaryStage(Stage stage) throws IOException {
        Infinity infinity = new Infinity();
        infinity.start(stage);
        menu = infinity.loadMenu(stage);
    }
}