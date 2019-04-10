package com.meti.lib.fx;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/6/2019
 */
@ExtendWith(ApplicationExtension.class)
class FXUtilTestWithFX {

    @Test
    void callNoThrows() {
        Future<Stage> future = FXUtil.call(Stage::new);
        assertDoesNotThrow(() -> {
            Stage stage = future.get();
            assertNotNull(stage);
        });
    }

    @Test
    void callThrows() {
        IllegalStateException exception = new IllegalStateException();
        Future<Stage> future = FXUtil.call(() -> {
            throw exception;
        });

        try {
            future.get();
        } catch (InterruptedException e) {
            fail("Future was not supposed to be interrupted.");
        } catch (ExecutionException e) {
            assertEquals(exception, e.getCause());
        }
    }

    @Test
    void throwIfNotFX() {
        Platform.runLater(() -> assertDoesNotThrow(FXUtil::throwIfNotFX));
    }
}