package com.meti.app.core.launch;

import com.meti.app.ApplicationLauncher;
import com.meti.app.core.runtime.Infinity;
import javafx.application.Application;
import javafx.stage.Stage;

import static java.util.Objects.requireNonNull;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/22/2019
 */
public class Main extends Application {
    private static final InfinityImpl infinity = new Infinity();
    private static final ApplicationLauncher launcher = createLauncher();

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

    private static InfinityLauncher createLauncher() {
        try {
            return new InfinityLauncher();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        requireNonNull(infinity).start(primaryStage);
    }

    @Override
    public void stop() {
        requireNonNull(infinity).stop();
    }
}
