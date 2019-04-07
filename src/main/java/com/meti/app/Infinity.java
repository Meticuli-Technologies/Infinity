package com.meti.app;

import com.meti.lib.log.ConsoleKey;
import com.meti.lib.log.LoggerConsole;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.net.URL;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
class Infinity implements InfinityImpl {
    final LoggerConsole console = new LoggerConsole();
    private boolean running;

    {
        console.eventManager.compound(ConsoleKey.ON_LOG, new ExitConsumer(Platform::exit));
    }

    @Override
    public void start(Stage primaryStage) {
        if(running){
            throw new IllegalStateException("Infinity is already running!");
        }

        this.running = true;

        primaryStage.show();
    }

    @Override
    public void stop() {
        if(!running){
            throw new IllegalStateException("Infinity is not running yet!");
        }

        this.running = false;
    }

    URL getMenuURL() {
        return getClass().getResource("/com/meti/app/Menu.fxml");
    }
}
