package com.meti.app;

import com.meti.lib.fx.ControllerLoader;
import com.meti.lib.fx.InfinityState;
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
public class Infinity extends Application {
    private static final Path PROPERTIES_PATH = Paths.get("Infinity.properties");

    private InfinityState state;
    private Properties properties;
    private Logger logger;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        logger = LoggerFactory.getLogger(Infinity.class);

        try {
            properties = loadProperties();
            state = new InfinityState(
                    primaryStage,
                    logger,
                    properties
            );

            primaryStage.setScene(new Scene(ControllerLoader.load(getClass().getResource("/com/meti/app/Menu.fxml"), state)));
            primaryStage.show();

            String mainStageXToken = properties.getProperty("mainStageX");
            String mainStageYToken = properties.getProperty("mainStageY");

            if (mainStageXToken != null && mainStageYToken != null) {
                try {
                    double mainStageX = Double.parseDouble(mainStageXToken);
                    double mainStageY = Double.parseDouble(mainStageYToken);

                    primaryStage.setX(mainStageX);
                    primaryStage.setY(mainStageY);
                } catch (NumberFormatException e) {
                    logger.error("Invalid dimensions: " + mainStageXToken + ", " + mainStageYToken);
                }
            }
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
    public void stop() {
        boolean error = false;

        try {
            stopServer();
        } catch (Exception e) {
            logger.error("Failed to stop server", e);
            error = true;
        }

        finalizeControllers();

        try {
            storeProperties();
        } catch (IOException e) {
            logger.error("Failed to store properties", e);
            error = true;
        }

        if (!error) {
            System.exit(0);
        } else {
            System.exit(-1);
        }
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
        Optional<Stage> stageOptional = state.firstOfType(Stage.class);
        if (stageOptional.isPresent()) {
            Stage stage = stageOptional.get();
            properties.setProperty("mainStageX", String.valueOf(stage.getX()));
            properties.setProperty("mainStageY", String.valueOf(stage.getY()));
        }

        properties.store(Files.newOutputStream(PROPERTIES_PATH), "");
    }
}
