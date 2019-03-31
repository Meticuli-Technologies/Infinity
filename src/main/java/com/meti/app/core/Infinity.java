package com.meti.app.core;

import javafx.stage.Stage;

import java.io.Closeable;
import java.io.IOException;

import static com.meti.lib.fx.ControllerLoader.load;
import static com.meti.lib.trys.TryableFactory.DEFAULT_FACTORY;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/31/2019
 */
public class Infinity {
    private final InfinityState state = new InfinityState();

    public void start(Stage primaryStage){
        state.add(primaryStage);

        try {
            load(getClass().getResource("/com/meti/app/control/Menu.fxml"), state);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        try {
            state.search(Closeable.class)
                    .forEach(DEFAULT_FACTORY.newConsumer(Closeable::close));
            DEFAULT_FACTORY.catcher.throwAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
