package com.meti.app;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(ApplicationExtension.class)
class LocalTest {
    private Local local;

    @Test
    void back() {
        assertDoesNotThrow(() -> local.back());
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