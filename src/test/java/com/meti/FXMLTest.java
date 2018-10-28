package com.meti;

import com.meti.lib.fx.ControllerLoader;
import com.meti.lib.fx.FXUtil;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/28/2018
 */
public abstract class FXMLTest<T> extends ClientTest {
    protected T controller;

    @Override
    void start(Stage primaryStage) throws IOException {
        super.start(primaryStage);

        ControllerLoader loader = new ControllerLoader(getFxmlUrl());
        FXUtil.onto(loader.load(), primaryStage);

        controller = loader.getController();
    }

    public abstract URL getFxmlUrl();
}
