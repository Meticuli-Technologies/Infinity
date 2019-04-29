package com.meti.lib.fx;

import com.meti.lib.util.CollectionUtil;
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
    private final ArrayList<Stage> stages = new ArrayList<>();

    public Stage createFromRoot(Parent root) {
        return createFromScene(new Scene(root));
    }

    public Stage createFromScene(Scene scene) {
        Stage stage = createWithEmptyScene();
        stage.setScene(scene);
        return stage;
    }

    public Stage createWithEmptyScene() {
        Stage toAdd = new Stage();
        if (!stages.isEmpty()) {
            setCoordinatesFromLastOf(toAdd);
        }
        return add(toAdd);
    }

    private void setCoordinatesFromLastOf(Stage toAdd) {
        Stage lastStage = CollectionUtil.lastStage(stages);
        double lastStageX = lastStage.getX();
        double lastStageY = lastStage.getY();
        toAdd.setX(lastStageX);
        toAdd.setY(lastStageY);
    }

    public Stage add(Stage stage) {
        stages.add(stage);
        return stage;
    }
}
