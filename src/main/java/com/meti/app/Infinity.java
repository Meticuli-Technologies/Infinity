package com.meti.app;

import com.meti.lib.console.Console;
import javafx.stage.Stage;

import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public class Infinity {
    //nonfinal variable is OK, because this variable will be injected
    private InfinityState state;
    private Console console;

    public void start(Stage primaryStage) {
        console = new Console(Infinity.class.getSimpleName());
        console.log(Level.INFO, "Starting Infinity");

        try {
            state = new InfinityState(
                    primaryStage,
                    console
            );
        } catch (Exception e) {
            console.log(Level.SEVERE, "Exception in starting Infinity", e);
        }
    }

    public void stop() {
        console.log(Level.INFO, "Stopping Infinity");
    }
}
