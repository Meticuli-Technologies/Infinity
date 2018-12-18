package com.meti.app;

import com.meti.lib.State;
import com.meti.lib.convert.DoubleConverter;
import com.meti.lib.convert.LongConverter;
import com.meti.lib.fx.ControllerLoader;
import com.meti.lib.fx.Finalizable;
import com.meti.lib.net.client.Client;
import com.meti.lib.net.connect.SocketConnection;
import com.meti.lib.net.server.Server;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
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
import java.util.concurrent.Executors;

import static com.meti.lib.util.PropertiesUtil.fromProperties;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class Infinity extends Application {
    private static final Path PROPERTIES_PATH = Paths.get("Infinity.properties");
    private State state;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Logger logger = LoggerFactory.getLogger(Infinity.class);

        //load properties
        Properties properties;
        try {
            properties = loadProperties();
        } catch (Exception e) {
            logger.error("Failed to load properties, continuing with empty set!", e);
            properties = new Properties();
        }

        //load stage position
        Double mainStageX = fromProperties(properties, "mainStageX", new DoubleConverter());
        Double mainStageY = fromProperties(properties, "mainStageY", new DoubleConverter());
        loadPrimaryStage(primaryStage, mainStageX, mainStageY);

        //assemble state
        state = new State(
                logger,
                properties,
                primaryStage,
                getHostServices(),
                Executors.newCachedThreadPool()
        );

        //load menu
        try {
            loadMenu(primaryStage);
        } catch (IOException e) {
            logger.error("Failed to load Menu.fxml", e);
        }
    }

    private void loadPrimaryStage(Stage primaryStage, double mainStageX, double mainStageY) {
        Rectangle2D bounds = Screen.getPrimary().getBounds();
        if (mainStageX > bounds.getWidth()) {
            mainStageX = 0d;
        }
        if (mainStageY > bounds.getHeight()) {
            mainStageY = 0d;
        }

        primaryStage.setX(mainStageX);
        primaryStage.setY(mainStageY);
    }

    private void loadMenu(Stage primaryStage) throws IOException {
        primaryStage.setScene(ControllerLoader.loadToScene(getClass().getResource("/com/meti/app/Menu.fxml"), state));
        primaryStage.setTitle("Welcome to Infinity");
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
            state.getLogger().error("Exception in stopping", e);
            System.exit(-1);
        }

        System.exit(0);
    }

    private void stopServer() throws Exception {
        Optional<Server> serverOptional = state.firstOfType(Server.class);
        if (serverOptional.isPresent()) {
            Server server = serverOptional.get();

            Optional<Set<Client<SocketConnection>>> clientSetOptional = state.getProperties().containsKey("stop_duration") ?
                    stopWithDuration(server) :
                    stopWithoutDuration(server);

            if (clientSetOptional.isPresent()) {
                state.getLogger().error(listClients(clientSetOptional.get()));
            } else {
                state.getLogger().info("All clients stopped cleanly");
            }
        } else {
            state.getLogger().info("No server found to stop");
        }
    }

    private Optional<Set<Client<SocketConnection>>> stopWithDuration(Server server) throws Exception {
        return server.stop(Duration.ofMillis(fromProperties(state.getProperties(), "stop_duration", new LongConverter())));
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
            state.getProperties().setProperty("mainStageX", String.valueOf(stage.getX()));
            state.getProperties().setProperty("mainStageY", String.valueOf(stage.getY()));
        }

        state.getProperties().store(Files.newOutputStream(PROPERTIES_PATH), "");
    }
}
