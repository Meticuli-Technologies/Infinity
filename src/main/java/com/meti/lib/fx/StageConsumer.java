package com.meti.lib.fx;

import com.meti.lib.tuple.Tuple3;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/31/2019
 */
public class StageConsumer extends FXConsumer {
    @Override
    public void accept(Tuple3<Stage, FXWizard<?>, Parent> tuple) {
        Stage stage = tuple.a;
        stage.setOnCloseRequest(event -> tuple.b.close());
        stage.setScene(new Scene(tuple.c));
        stage.showAndWait();
    }
}
