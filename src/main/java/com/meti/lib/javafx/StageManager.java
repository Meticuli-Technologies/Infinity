package com.meti.lib.javafx;

import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/3/2019
 */
public interface StageManager {
    void addStage(Stage stage);

    void loadPrimaryStage(Parent parent);
}
