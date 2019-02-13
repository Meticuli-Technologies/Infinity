package com.meti;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/13/2019
 */
public class Main extends Application {
    private final Infinity context;

    /**
     * <p>
     *     The default constructor is called by {@link Application#launch(String...)},
     *     which is then called in {@link #main(String[])}.
     * </p>
     */
    @SuppressWarnings("unused")
    public Main() {
        this(new Infinity());
    }

    /**
     * <p>
     *     This constructor is not instantiated by JavaFX application and only serves
     *     to verify that components inside of Main are successfully being tested.
     * </p>
     * @param context The context to use.
     */
    public Main(Infinity context) {
        this.context = context;
    }

    @Override
    public void start(Stage primaryStage) {
        context.start(primaryStage);
    }

    @Override
    public void stop() {
        context.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
