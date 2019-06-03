package com.meti.lib.fx;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/3/2019
 */
public class ListBasedStageManager implements StageManager {
    private static final int PRIMARY_STAGE_INDEX = 0;
    private final List<Stage> stages = new ArrayList<>();

    @Override
    public void addStage(Stage stage) {
        stages.add(stage);
    }

    @Override
    public void loadPrimaryStage(Parent parent) {
        load(PRIMARY_STAGE_INDEX, parent);
    }

    private void load(int index, Parent parent) {
        Stage stage = stages.get(index);
        stage.setScene(new Scene(parent));
        if (!stage.isShowing()) {
            stage.show();
        }
    }
}
