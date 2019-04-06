package com.meti.lib.manage;

import com.meti.lib.fx.FXUtil;
import javafx.stage.Stage;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/6/2019
 */
class StageManager extends Manager<Stage> {
    @Override
    public Stage allocate() {
        FXUtil.throwIfNotFX();
        return new Stage();
    }
}
