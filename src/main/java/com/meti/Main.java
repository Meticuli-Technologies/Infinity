package com.meti;

import com.meti.control.ControllerLoader;
import com.meti.control.depend.DependencyManager;
import com.meti.control.depend.window.WindowedDependency;
import com.meti.control.depend.window.WindowedDependencyFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/5/2018
 */
public class Main extends Application {
    public static MainState state = new MainState();

    @Override
    public void start(Stage primaryStage) throws Exception {
        WindowedDependency.defaultDependencyManager = state.dependencyManager;

        state.dependencyManager.addDependencyFactory(new WindowedDependencyFactory(primaryStage));
        state.dependencyManager.addDependencyFactory(new ExceptionDependencyFactory(state.console.getExceptionSupplier(Level.SEVERE)));

        primaryStage.setScene(new Scene(ControllerLoader.load(
                getClass().getResource("/com/meti/app/Menu.fxml"),
                state.dependencyManager)
        ));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static class MainState {
        public final DependencyManager dependencyManager = new DependencyManager();
        public final Console console = new Console();

        private MainState() {
        }
    }
}
