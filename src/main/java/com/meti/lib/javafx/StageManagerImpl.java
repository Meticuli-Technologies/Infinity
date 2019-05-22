package com.meti.lib.javafx;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public interface StageManagerImpl {
    Stage ontoPrimaryStage(Scene scene);

    Stage ontoLastStage(Scene scene);

    Stage ontoStage(Scene scene, int index);
}
