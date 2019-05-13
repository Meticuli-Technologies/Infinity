package com.meti;

import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class StageManager {
    private final List<Stage> stages = new ArrayList<>();

    public Stage addStage(Stage stage) {
        this.stages.add(stage);
        return stage;
    }

    public Stage allocate() {
        Stage stage = new Stage();
        stages.add(stage);
        return stage;
    }

    private boolean containsStageIndex(int index) {
        return stages.size() > index;
    }

    public Stage getPrimaryStage() {
        return getStage(0);
    }

    public Stage getStage(int index) {
        if (containsStageIndex(index)) {
            return stages.get(index);
        } else {
            allocate();
            return getStage(index);
        }
    }
}
