package com.meti.lib.manage;

import javafx.application.Platform;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/6/2019
 */
@ExtendWith(ApplicationExtension.class)
class StageManagerTest {
    @Test
    void allocate(){
        Platform.runLater(() -> assertDoesNotThrow(() ->
                assertNotNull(new StageManager().allocate())));
    }

}
