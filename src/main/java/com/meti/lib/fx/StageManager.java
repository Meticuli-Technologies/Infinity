package com.meti.lib.fx;

import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public class StageManager {
    private final List<Stage> stages = new ArrayList<>();

    public StageManager(Stage primaryStage) {
        stages.add(primaryStage);
    }
}
