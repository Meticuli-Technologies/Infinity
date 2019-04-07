package com.meti.app;

import com.meti.lib.fx.FXMLBundle;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
class InfinityNoFXTest {
    private Infinity infinity;

    @BeforeEach
    void beforeEach() {
        this.infinity = new Infinity();
    }

    @Test
    void start() {
        //Check start.
        assertDoesNotThrow(() -> infinity.start(Mockito.mock(Stage.class)));
        assertNotNull(infinity.console);
        assertNotNull(infinity.state);

        //If Infinity was already started, it shouldn't be able to be started again unless it has been stopped.
        assertThrows(IllegalStateException.class, () -> infinity.start(Mockito.mock(Stage.class)));

        //Cleanly stop Infinity.
        assertDoesNotThrow(() -> infinity.stop());
    }


    @Test
    void getMenuURL(){
        URL url = infinity.getMenuURL();
        assertNotNull(url);
        assertTrue(url.getPath().endsWith("/com/meti/app/Menu.fxml"));
    }

    @Test
    void getMenuBundle() throws IOException {
        FXMLBundle<?> bundle = infinity.getMenuBundle();
        assertEquals(AnchorPane.class, bundle.parent.getClass());
        assertEquals(Menu.class, bundle.controller.getClass());
    }

    @Test
    void stop() {
        //Infinity hasn't been running, so it doesn't make sense to call stop when it's not running in the first place.
        assertThrows(IllegalStateException.class, () -> infinity.stop());

        //Check stop cleanly.
        assertDoesNotThrow(() -> infinity.start(Mockito.mock(Stage.class)));
        assertDoesNotThrow(() -> infinity.stop());
    }
}