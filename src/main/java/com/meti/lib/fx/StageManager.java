package com.meti.lib.fx;

import javafx.stage.Stage;

import java.util.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/29/2019
 */
public class StageManager {
    public final List<Stage> stages = new ArrayList<>();
    private final Queue<int[]> coordinateQueue = new LinkedList<>();

    public StageManager(int[]... coordinates) {
        coordinateQueue.addAll(Arrays.asList(coordinates));
    }

    public StageManager(Collection<int[]> coordinates) {
        coordinateQueue.addAll(coordinates);
    }

    public void add(Stage stage) {
        stages.add(stage);

        assignCoordinates(stage);
    }

    private boolean assignCoordinates(Stage stage) {
        if (!coordinateQueue.isEmpty()) {
            int[] coordinates = coordinateQueue.poll();
            stage.setX(coordinates[0]);
            stage.setY(coordinates[1]);
            return true;
        }
        return false;
    }

    public Stage getPrimaryStage() {
        return get(0);
    }

    public Stage get(int index) {
        if (stages.size() > index) {
            return stages.get(index);
        } else {
            return allocate();
        }
    }

    public Stage allocate() {
        Stage stage = new Stage();
        stages.add(stage);
        assignCoordinates(stage);
        return stage;
    }
}
