package com.meti.lib.javafx;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public class StageManager implements StageManagerImpl {
    private final List<Stage> stages = new ArrayList<>();

    public StageManager(Stage primaryStage) {
        stages.add(primaryStage);
    }

    private Stage until(int index) {
        do {
            try {
                return stages.get(index);
            } catch (IndexOutOfBoundsException e) {
                stages.add(new Stage());
            }
        } while (true);
    }

    @Override
    public Stage ontoPrimaryStage(Scene scene) {
        return this.ontoStage(scene, 0);
    }

    @Override
    public Stage ontoLastStage(Scene scene) {
        return this.ontoStage(scene, stages.size() - 1);
    }

    @Override
    public Stage ontoStage(Scene scene, int index) {
        Stage stage = this.until(index);
        stage.setScene(scene);
        if (!stage.isShowing()) stage.show();
        return stage;
    }
}
