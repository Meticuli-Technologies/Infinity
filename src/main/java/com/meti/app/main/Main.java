package com.meti.app.main;

import com.meti.lib.State;
import com.meti.lib.fx.ControllerLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main extends Application {
    private final ExecutorService service = Executors.newCachedThreadPool();
    private final State state = new State();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        state.add(primaryStage);
        state.add(service);

        Parent load = ControllerLoader.load(getClass().getResource("/com/meti/app/control/Menu.fxml"), state);
        primaryStage.setScene(new Scene(load));
        primaryStage.show();
    }

    @Override
    public void stop() {
        close();

        try {
            service.shutdown();

            if (!service.isTerminated()) {
                Thread.sleep(5000);
                service.shutdownNow();
            }

            if (service.isTerminated()) {
                System.out.println("Stopped server successfully.");
                System.exit(0);
            } else {
                System.out.println("Failed to stop server normally.");
                System.exit(-1);
            }
        } catch (InterruptedException e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }

    private void close() {
        String exceptionString = state.byClass(Closeable.class)
                .map((Function<Closeable, Optional<Exception>>) closeable -> {
                    try {
                        closeable.close();
                        return Optional.empty();
                    } catch (IOException e) {
                        return Optional.of(e);
                    }
                })
                .flatMap(Optional::stream)
                .map(e -> {
                    StringWriter writer = new StringWriter();
                    e.printStackTrace(new PrintWriter(writer));
                    return writer.toString();
                }).collect(Collectors.joining("\n\t"));

        if (exceptionString.length() == 0) {
            System.out.println("Stopped application with no exceptions.");
        } else {
            System.out.println("Stopped application with exceptions:\n" + exceptionString);
        }
    }
}
