package com.meti.app;

import com.meti.lib.fx.FXMLBundle;
import com.meti.lib.fx.FXUtil;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/7/2019
 */
@ExtendWith(ApplicationExtension.class)
class InfinityTest {
    private final Infinity infinity = new Infinity();
    private Stage primaryStage;

    @Test
    void confirmStart() {
        assertNotNull(infinity.console);
        assertNotNull(infinity.factory);
        assertNotNull(infinity.state);
        assertTrue(primaryStage.isShowing());
    }

    @Test
    void getMenuBundle() throws IOException {
        FXMLBundle<?> bundle = infinity.getMenuBundle();
        assertEquals(AnchorPane.class, bundle.parent.getClass());
        assertEquals(Menu.class, bundle.controller.getClass());
    }

    @Test
    void getMenuURL() {
        URL url = Infinity.getMenuURL();
        assertNotNull(url);
        assertTrue(url.getPath().endsWith("/com/meti/app/Menu.fxml"));
    }

    @Test
    void loadMenu() throws ExecutionException, InterruptedException {
        Stage stage = com.meti.lib.fx.FXUtil.call(Stage::new).get();
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
