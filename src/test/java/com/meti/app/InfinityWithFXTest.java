package com.meti.app;

import com.meti.lib.util.FXUtil;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void loadMenu() throws ExecutionException, InterruptedException {
        Stage stage = FXUtil.call(Stage::new).get();
        Menu menu = FXUtil.call(() -> infinity.loadMenu(stage)).get();

        assertNotNull(menu);
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
