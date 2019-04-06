package com.meti.lib.fx;

import javafx.application.Platform;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/6/2019
 */
@ExtendWith(ApplicationExtension.class)
class FXUtilTestWithFX {

    @Test
    void throwIfNotFX() {
        Platform.runLater(() -> assertDoesNotThrow(FXUtil::throwIfNotFX));
    }
}