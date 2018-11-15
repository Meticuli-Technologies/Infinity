package com.meti.app;

import com.meti.lib.fx.ControllerLoader;
import com.meti.lib.fx.ControllerState;
import com.meti.lib.net.client.Client;
import com.meti.lib.net.connect.SocketConnection;
import com.meti.lib.net.server.Server;
import com.meti.lib.util.Finalizable;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class Main extends Application {
    private static final Path PROPERTIES_PATH = Paths.get("Infinity.properties");

    private ControllerState state;
    private Properties properties;
    private Logger logger;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        logger = LoggerFactory.getLogger(Main.class);

        try {
            properties = loadProperties();
            state = new ControllerState(
                    primaryStage,
                    logger,
                    properties
            );

            primaryStage.setScene(new Scene(ControllerLoader.load(getClass().getResource("/com/meti/app/Menu.fxml"), state)));
            primaryStage.show();
        } catch (IOException e) {
            logger.error("Failed to start application", e);
        }
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
            Optional<Set<Client<SocketConnection>>> clientSetOptional;
            if (properties.containsKey("stop_duration")) {
                long milis;
                try {
                    milis = Long.parseLong(properties.getProperty("stop_duration"));
                } catch (NumberFormatException e) {
                    milis = 1000;
                }
                clientSetOptional = serverOptional.get().stop(Duration.ofMillis(milis));
            } else {
                clientSetOptional = serverOptional.get().stop();
            }

            if (clientSetOptional.isPresent()) {
                Set<Client<SocketConnection>> clientSet = clientSetOptional.get();
                StringBuilder builder = new StringBuilder();
                builder.append("Located ")
                        .append(clientSet.size())
                        .append(" clients that did not stop cleanly: ");

                clientSet.forEach(socketConnectionClient -> builder.append("\n\t")
                        .append(socketConnectionClient.connection.socket.getInetAddress()));

                logger.error(builder.toString());
            } else {
                logger.info("All clients stopped cleanly");
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
