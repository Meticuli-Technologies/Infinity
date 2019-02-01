package com.meti.lib.fx;

import com.meti.lib.tuple.Tuple3;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/31/2019
 */
public abstract class FXConsumer implements Consumer<Tuple3<Stage, FXWizard<?>, Parent>> {
}
