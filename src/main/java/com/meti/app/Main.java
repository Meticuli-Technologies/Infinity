package com.meti.app;

import javafx.application.Application;
import javafx.stage.Stage;

import static java.util.Objects.requireNonNull;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/22/2019
 */
public class Main extends Application  {
    static ApplicationLauncher launcher;

    static {
        try {
            launcher = new InfinityLauncher();
        } catch (NoSuchMethodException e) {
            emergencyExit(e);
        }
    }

    public static void main(String[] args) {
        try {
            requireNonNull(launcher).launch(args);
        } catch (Exception e) {
            emergencyExit(e);
        }
    }

    private static void emergencyExit(Exception e) {
        e.printStackTrace();
        System.exit(-1);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
