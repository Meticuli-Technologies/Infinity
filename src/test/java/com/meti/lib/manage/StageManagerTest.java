package com.meti.lib.manage;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/6/2019
 */
class StageManagerTest {
    @Test
    void allocate(){
        assertNotNull(new StageManager().allocate());
    }

    private class StageManager extends Manager<Stage> {
        @Override
        public Stage allocate() {
            return new Stage();
        }
    }
}
