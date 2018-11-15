package com.meti.app;

import com.meti.lib.fx.ControllerLoader;
import com.meti.lib.fx.ControllerState;
import com.meti.lib.net.server.Server;
import com.meti.lib.util.Finalizable;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Optional;
import java.util.Properties;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class Main extends Application {
    private static final Path PROPERTIES_PATH = Paths.get("Infinity.properties");

    private ControllerState state;
    private Properties properties;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        properties = loadProperties();
        state = new ControllerState(
                primaryStage,
                LoggerFactory.getLogger(Main.class),
                properties
        );

        primaryStage.setScene(new Scene(ControllerLoader.load(getClass().getResource("/com/meti/app/Menu.fxml"), state)));
        primaryStage.show();
    }

    private Properties loadProperties() throws IOException {
        Properties properties = new Properties();

        if (!Files.exists(PROPERTIES_PATH)) {
            Files.createFile(PROPERTIES_PATH);
        }

        properties.load(Files.newInputStream(PROPERTIES_PATH));
        return properties;
    }

    @Override
    public void stop() throws Exception {
        stopServer();
        finalizeControllers();
        storeProperties();
    }

    private void stopServer() throws Exception {
        Optional<Server> serverOptional = state.firstOfType(Server.class);
        if (serverOptional.isPresent()) {
            if (properties.containsKey("stop_duration")) {
                long milis;
                try {
                    milis = Long.parseLong(properties.getProperty("stop_duration"));
                } catch (NumberFormatException e) {
                    milis = 1000;
                }
                serverOptional.get().stop(Duration.ofMillis(milis));
            } else {
                serverOptional.get().stop();
            }
        }
    }

    private void finalizeControllers() {
       state.ofType(Finalizable.class).forEach(Finalizable::finalizeController);
    }

    private void storeProperties() throws IOException {
        properties.store(Files.newOutputStream(PROPERTIES_PATH), "");
    }
}
