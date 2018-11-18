package com.meti.app;

import com.meti.lib.convert.Converter;
import com.meti.lib.convert.DoubleConverter;
import com.meti.lib.convert.LongConverter;
import com.meti.lib.fx.ControllerLoader;
import com.meti.lib.fx.Finalizable;
import com.meti.lib.State;
import com.meti.lib.net.client.Client;
import com.meti.lib.net.connect.SocketConnection;
import com.meti.lib.net.server.Server;
import javafx.application.Application;
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

    private State state;
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
            state = new State(
                    primaryStage,
                    logger,
                    properties
            );

            buildPrimaryStage(primaryStage);
        } catch (Exception e) {
            logger.error("Failed to start application", e);
        }
    }

    private void buildPrimaryStage(Stage primaryStage) throws IOException {
        primaryStage.setX(Converter.fromProperties(properties, "mainStageX", new DoubleConverter()));
        primaryStage.setY(Converter.fromProperties(properties, "mainStageY", new DoubleConverter()));

        primaryStage.setScene(ControllerLoader.loadToScene(getClass().getResource("/com/meti/app/Menu.fxml"), state));
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
    public void stop() {
        finalizeControllers();

        try {
            stopServer();
            storeProperties();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception in stopping", e);
            System.exit(-1);
        }

        System.exit(0);
    }

    private void stopServer() throws Exception {
        Optional<Server> serverOptional = state.firstOfType(Server.class);
        if (serverOptional.isPresent()) {
            Server server = serverOptional.get();

            Optional<Set<Client<SocketConnection>>> clientSetOptional = properties.containsKey("stop_duration") ?
                    stopWithDuration(server) :
                    stopWithoutDuration(server);

            if (clientSetOptional.isPresent()) {
                logger.error(listClients(clientSetOptional.get()));
            } else {
                logger.info("All clients stopped cleanly");
            }
        } else {
            logger.info("No server found to stop");
        }
    }

    private Optional<Set<Client<SocketConnection>>> stopWithDuration(Server server) throws Exception {
        return server.stop(Duration.ofMillis(Converter.fromProperties(properties, "stop_duration", new LongConverter())));
    }

    private Optional<Set<Client<SocketConnection>>> stopWithoutDuration(Server server) throws Exception {
        return server.stop();
    }

    private String listClients(Set<Client<SocketConnection>> clientSet) {
        StringBuilder builder = new StringBuilder();
        builder.append("Located ")
                .append(clientSet.size())
                .append(" clients that did not stop cleanly: ");

        clientSet.forEach(socketConnectionClient -> builder.append("\n\t")
                .append(socketConnectionClient.connection.socket.getInetAddress()));

        return builder.toString();
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
