package com.meti.app;

import com.meti.lib.console.Console;
import com.meti.lib.fx.ControllerLoader;
import com.meti.lib.fx.StageManager;
import com.meti.lib.module.ModuleManager;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public class Infinity {
    private static final Path PROPERTIES_DEFAULT_PATH = Paths.get(".\\Infinity.properties");

    //nonfinal variable is OK, because this variable will be injected
    private InfinityState state;
    private Console console;
    private Properties properties;
    private ModuleManager moduleManager;
    private StageManager stageManager;

    public void start(Stage primaryStage) {
        console = new Console(Infinity.class.getSimpleName());
        console.log(Level.INFO, "Starting Infinity");

        try {
            properties = loadProperties();
            moduleManager = loadModules(properties);
            stageManager = loadStageManager(properties);
            stageManager.add(primaryStage);

            state = new InfinityState(
                    properties,
                    moduleManager,
                    stageManager,
                    console
            );

            loadMenu(stageManager.get());
        } catch (Exception e) {
            console.log(Level.SEVERE, "Exception in starting Infinity", e);
        }
    }

    private StageManager loadStageManager(Properties properties) {
        String initial_coordinates = properties.getProperty("initial_coordinates");
        if (initial_coordinates != null) {
            return new StageManager(Arrays.stream(initial_coordinates.split(","))
                    .map(s -> s.split(" "))
                    .map(strings -> {
                        int[] array = new int[strings.length];
                        array[0] = Integer.parseInt(strings[0]);
                        array[1] = Integer.parseInt(strings[1]);
                        return array;
                    })
                    .collect(Collectors.toSet()));
        }
        else{
            return new StageManager();
        }
    }

    private ModuleManager loadModules(Properties properties) throws IOException {
        String moduleDirectoryString = properties.getProperty("module_directory");

        if (moduleDirectoryString != null) {
            ModuleManager manager = new ModuleManager();
            manager.loadModules(Paths.get(moduleDirectoryString));
            return manager;
        }
        else{
            throw new IllegalStateException("Cannot find the property of \"module directory\"");
        }
    }

    private Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        if (!Files.exists(PROPERTIES_DEFAULT_PATH)) {
            Files.createFile(PROPERTIES_DEFAULT_PATH);

            loadDefaultProperties(properties);
        } else {
            properties.load(Files.newInputStream(PROPERTIES_DEFAULT_PATH));
        }
        return properties;
    }

    private void loadDefaultProperties(Properties properties) {
        properties.setProperty("version", "1.0");
        properties.setProperty("module_directory", ".\\modules");
    }

    private void loadMenu(Stage primaryStage) throws java.io.IOException {
        primaryStage.setScene(new Scene(ControllerLoader.load(getMenuURL(), state)));
        primaryStage.show();
    }

    private URL getMenuURL() {
        return getClass().getResource("/com/meti/app/Menu.fxml");
    }

    public void stop() {
        console.log(Level.INFO, "Stopping Infinity");

        try {
            storeProperties();
        } catch (IOException e) {
            console.log(Level.SEVERE, "Exception in stopping Infinity", e);
        }
    }

    private void storeProperties() throws IOException {
        String coordinateString = stageManager.stages.stream()
                .map(stage -> new int[]{(int) stage.getX(), (int) stage.getY()})
                .map(ints -> new String[]{String.valueOf(ints[0]), String.valueOf(ints[1])})
                .map(strings -> strings[0] + " " + strings[1])
                .collect(Collectors.joining(","));
        properties.setProperty("initial_coordinates", coordinateString);

        properties.store(Files.newOutputStream(PROPERTIES_DEFAULT_PATH), null);
    }
}
