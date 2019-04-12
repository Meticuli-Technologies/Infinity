package com.meti.app;

import com.meti.lib.control.ControllerLoader;
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
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class Main extends Application {
    private final ExecutorService service = Executors.newCachedThreadPool();
    private State state = new State();

    @Override
    public void start(Stage primaryStage) throws Exception {
        state.add(service);
        state.add(primaryStage);

        primaryStage.setScene(new Scene(ControllerLoader.load(getClass().getResource("/com/meti/Local.fxml"), state)));
        primaryStage.show();
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
            System.err.println(exceptionLine);
        }

        List<Runnable> runnables = service.shutdownNow();
        StringBuilder builder = new StringBuilder("Shutdown the server with " + runnables.size() + " runnables still running:");
        builder.append(runnables.stream().map(Object::toString)
                .collect(Collectors.joining("\n\t")));

        System.out.println(builder);
        if (!service.isTerminated()) {
            throw new IllegalStateException("Executor service has not been terminated successfully!");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
