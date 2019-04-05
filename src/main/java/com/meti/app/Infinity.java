package com.meti.app;

import com.meti.lib.log.LoggerConsole;
import javafx.stage.Stage;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
class Infinity implements InfinityImpl {
    public final LoggerConsole console = new LoggerConsole();
    private boolean running;

    @Override
    public void start(Stage primaryStage) {
        if(running){
            throw new IllegalStateException("Infinity is already running!");
        }

        this.running = true;
    }

    @Override
    public void stop() {
        if(!running){
            throw new IllegalStateException("Infinity is not running yet!");
        }

        this.running = false;
    }
}
