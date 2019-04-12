package com.meti.app;

import com.meti.lib.control.ControllerLoader;
import com.meti.lib.log.Console;
import com.meti.lib.util.State;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class Main extends Application {
    private final ExecutorService service = Executors.newCachedThreadPool();
    private final Console console = new Console();
    private final State state = new State();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            state.add(service);
            state.add(console);
            state.add(primaryStage);

            primaryStage.setScene(new Scene(ControllerLoader.load(getClass().getResource("/com/meti/Menu.fxml"), state)));
            primaryStage.show();
        } catch (IOException e) {
            console.log(Level.SEVERE, e);
        }
    }

    @Override
    public void stop() {
        String exceptionLine = state.byClass(Closeable.class)
                .map((Function<Closeable, Optional<Exception>>) closeable -> {
                    try {
                        closeable.close();

                        return Optional.empty();
                    } catch (IOException e) {
                        return Optional.of(e);
                    }
                }).flatMap(Optional::stream)
                .map(e -> {
                    StringWriter writer = new StringWriter();
                    e.printStackTrace(new PrintWriter(writer));
                    return writer.toString();
                }).collect(Collectors.joining("\n\t"));
        if (exceptionLine.length() != 0) {
            console.log(Level.SEVERE, exceptionLine);
        }

        try {
            List<Runnable> runnables = service.shutdownNow();
            String builder = "Shutdown the server with " + runnables.size() + " runnables still running:" +
                    runnables.stream()
                            .map(Object::toString)
                            .collect(Collectors.joining("\n\t"));
            console.log(Level.INFO, builder);
            if (!service.isTerminated()) {
                throw new IllegalStateException("Executor service has not been terminated successfully!");
            }
        } catch (IllegalStateException e) {
            console.log(Level.SEVERE, e);
        }
    }
}
