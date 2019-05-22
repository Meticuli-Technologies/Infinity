package com.meti.app.core.init;

import com.meti.app.core.state.Toolkit;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public interface InitializerImpl {
    Toolkit init(Stage primaryStage) throws IOException;
}
