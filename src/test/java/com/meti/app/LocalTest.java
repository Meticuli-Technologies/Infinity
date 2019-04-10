package com.meti.app;

import com.meti.lib.fx.FXMLBundle;
import com.meti.lib.fx.FXUtil;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class LocalTest {
    private Local local;

    @Test
    void back() {
        assertFalse(local.back().isPresent());
    }

    @Test
    void loadMenu() {
        assertDoesNotThrow(() -> {
            FXMLBundle<Menu> menu = FXUtil.call(local::loadMenu).get();
            assertNotNull(menu.controller);
            assertEquals(menu.parent, local.stage.getScene().getRoot());
        });
    }

    @Test
    void next() {
        assertDoesNotThrow(() -> local.next());
    }

    @Start
    void start(Stage primaryStage) throws IOException {
        Infinity infinity = new Infinity();
        local = infinity.loadInitial(primaryStage, URLS.getLocalURL());
    }
}