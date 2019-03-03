package com.meti.app;

import com.meti.lib.State;
import com.meti.lib.TryableFactory;
import com.meti.lib.fx.ControllerLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.Closeable;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/24/2019
 */
public class Main extends Application {
    private final State state = new State();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            state.add(primaryStage);

            URL resource = getClass().getResource("/com/meti/app/Menu.fxml");
            primaryStage.setScene(new Scene(ControllerLoader.load(resource, state)));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        TryableFactory factory = new TryableFactory();
        List<Closeable> closeables = factory.checkAll(state.stream()
                .filter(o -> o instanceof Closeable)
                .map(o -> (Closeable) o)
                .peek(factory.accept(Closeable::close))
        ).collect(Collectors.toList());

        System.out.println("Ended Infinity with " + closeables.size() + " closeables");
    }
}
