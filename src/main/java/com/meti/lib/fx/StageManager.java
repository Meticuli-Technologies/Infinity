package com.meti.lib.fx;

import com.meti.lib.collect.manage.Manager;
import javafx.stage.Stage;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/6/2019
 */
class StageManager extends Manager<Stage> {
    public Stage allocate() {
        FXUtil.throwIfNotFX();
        return new Stage();
    }
}
