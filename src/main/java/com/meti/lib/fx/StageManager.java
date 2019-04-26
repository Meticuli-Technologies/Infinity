package com.meti.lib.fx;

import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/26/2019
 */
public class StageManager {
    private final ArrayList<Stage> stages = new ArrayList<>();

    public Stage create() {
        Stage toAdd = new Stage();
        if (!stages.isEmpty()) {
            setCoordinatesFromLastOf(toAdd);
        }
        return add(toAdd);
    }

    private void setCoordinatesFromLastOf(Stage toAdd) {
        Stage lastStage = lastStage();
        double lastStageX = lastStage.getX();
        double lastStageY = lastStage.getY();
        toAdd.setX(lastStageX);
        toAdd.setY(lastStageY);
    }

    public Stage add(Stage stage) {
        stages.add(stage);
        return stage;
    }

    public Stage lastStage() {
        return stages.get(lastStageIndex());
    }

    public int lastStageIndex() {
        if (stages.isEmpty()) {
            throw new NoSuchElementException("No elements present.");
        }
        return stages.size() - 1;
    }
}
