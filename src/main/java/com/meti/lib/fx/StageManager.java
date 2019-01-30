package com.meti.lib.fx;

import javafx.stage.Stage;

import java.util.*;
import java.util.function.Supplier;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/29/2019
 */
public class StageManager implements Supplier<Stage> {
    public final List<Stage> stages = new ArrayList<>();
    private final Queue<int[]> coordinateQueue = new LinkedList<>();

    public StageManager(Collection<int[]> initialCoordinates) {
        coordinateQueue.addAll(initialCoordinates);
    }

    public StageManager(int[]... initialCoordinates) {
        coordinateQueue.addAll(Arrays.asList(initialCoordinates));
    }

    @Override
    public Stage get() {
        Stage stage = new Stage();
        setCoordinates(stage);
        stages.add(stage);
        return stage;
    }

    private void setCoordinates(Stage stage) {
        int[] poll = coordinateQueue.poll();
        if (poll != null) {
            if (poll.length == 2) {
                stage.setX(poll[0]);
                stage.setY(poll[1]);
            } else {
                throw new IllegalStateException(poll.length + " is not equal to 2, invalid dimensions");
            }
        }
    }

    public void add(Stage primaryStage) {
        stages.add(primaryStage);

        setCoordinates(primaryStage);
    }
}
