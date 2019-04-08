package com.meti.app;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */ //Main MUST remain public in order for JavaFX Application to start.
public class Main extends Application {
    static InfinityImpl implementation = new Infinity();
    static Launcher launcher;

    static {
        try {
            launcher = new InfinityLauncher();

            //TODO: test catch block
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    static Main instance;

    public void start(Stage primaryStage) {
        instance = this;
        implementation.start(primaryStage);
    }

    @Override
    public void stop() {
        implementation.stop();
        instance = null;
    }

    public static void main(String[] args) throws Exception {
        launcher.invoke(Main.class, args);
    }
}
