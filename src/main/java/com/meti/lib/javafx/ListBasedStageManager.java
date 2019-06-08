package com.meti.lib.javafx;

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
    public void allocate(Parent root) {
        addStage(new Stage());
        loadLast(root);
    }

    @Override
    public void loadLast(Parent parent){
        loadParent(lastStageIndex(), parent);
    }

    private int lastStageIndex() {
        return stages.size() - 1;
    }

    @Override
    public void loadPrimaryStage(Parent parent) {
        loadParent(PRIMARY_STAGE_INDEX, parent);
    }

    //TODO: consider if other indices are required?
    private void loadParent(int index, Parent parent) {
        Stage stage = stages.get(index);
        stage.setScene(new Scene(parent));
        if (!stage.isShowing()) {
            stage.show();
        }
    }
}
