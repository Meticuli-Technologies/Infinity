package com.meti.app;

import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;

import java.net.URL;

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

    @Start
    void onStart(Stage primaryStage) {
        this.primaryStage = primaryStage;
        infinity.start(primaryStage);
    }

    @Test
    void getMenuURL(){
        URL url = infinity.getMenuURL();
        assertNotNull(url);
        assertTrue(url.getPath().endsWith("/com/meti/app/Menu.fxml"));
    }

    @Test
    void confirmStart(){
        assertTrue(primaryStage.isShowing());

    }

    @Stop
    void onStop() {
        infinity.stop();
    }
}
