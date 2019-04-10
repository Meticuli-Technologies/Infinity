package com.meti.app;

import com.meti.lib.collect.tryable.TryableFactory;
import com.meti.lib.collect.tryable.TryableSupplier;
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
    static Main instance;

    static {
        launcher = TryableFactory
                .DEFAULT
                .supplier((TryableSupplier<Launcher>) InfinityLauncher::new)
                .get()
                .orElseThrow();
    }

    public static void main(String[] args) throws Exception {
        launcher.invoke(Main.class, args);
    }

    public void start(Stage primaryStage) {
        instance = this;
        implementation.start(primaryStage);
    }

    @Override
    public void stop() {
        implementation.stop();
        instance = null;
    }
}
