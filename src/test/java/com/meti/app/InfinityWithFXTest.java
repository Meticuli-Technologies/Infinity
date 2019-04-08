package com.meti.app;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/7/2019
 */
@ExtendWith(ApplicationExtension.class)
class InfinityWithFXTest {
    private Infinity infinity = new Infinity();
    private Stage primaryStage;

    @Test
    void confirmStart() {
        assertNotNull(infinity.console);
        assertNotNull(infinity.factory);
        assertNotNull(infinity.state);
        assertTrue(primaryStage.isShowing());
    }

    @Test
    void loadMenu() throws IOException {
        Stage stage = new Stage();
        Menu menu = infinity.loadMenu(stage);

        assertNotNull(menu);

        assertEquals(infinity.getMenuBundle().parent, stage.getScene().getRoot());
        assertTrue(stage.isShowing());
    }

    @Start
    void onStart(Stage primaryStage) {
        this.primaryStage = primaryStage;
        infinity.start(primaryStage);
    }

    @Stop
    void onStop() {
        infinity.stop();
    }
}
