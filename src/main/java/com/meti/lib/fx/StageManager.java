package com.meti.lib.fx;

import com.meti.lib.collect.Collections;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/26/2019
 */
public class StageManager {
    public static final int NEW_STAGE = -1;
    private final ArrayList<Stage> stages = new ArrayList<>();

    public Stage setRootToStage(Parent root, int index) {
        return setSceneToStage(new Scene(root), index);
    }

    private Stage setSceneToStage(Scene scene, int index) {
        Stage stage = getStage(index);
        stage.setScene(scene);
        return stage;
    }

    private Stage getStage(int index) {
        if (index == NEW_STAGE) {
            return allocate();
        } else {
            return getIndex(index);
        }
    }

    private Stage allocate() {
        return setCoordinatesFromLastOf(add(new Stage()));
    }

    private Stage getIndex(int index) {
        while (!Collections.containsIndex(stages, index)) {
            allocate();
        }
        return stages.get(index);
    }

    private Stage setCoordinatesFromLastOf(Stage toAdd) {
        Stage lastStage = Collections.lastElement(stages).orElseThrow();
        double lastStageX = lastStage.getX();
        double lastStageY = lastStage.getY();
        toAdd.setX(lastStageX);
        toAdd.setY(lastStageY);
        return toAdd;
    }

    public Stage add(Stage stage) {
        stages.add(stage);
        return stage;
    }
}
