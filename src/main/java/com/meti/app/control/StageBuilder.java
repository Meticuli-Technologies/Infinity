package com.meti.app.control;

import com.meti.lib.collect.FunctionalConsumer;
import com.meti.lib.concurrent.CompletableConsumer;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.function.Consumer;

class StageBuilder extends FunctionalConsumer<Parent, Stage> {
    StageBuilder(Consumer<Stage> consumer) {
        super(consumer);
    }

    @Override
    public Stage apply(Parent parent) {
        return buildStage(parent);
    }

    private Stage buildStage(Parent root) {
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        return stage;
    }
}
