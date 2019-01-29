package com.meti.app;

import com.meti.lib.console.Console;
import com.meti.lib.fx.ControllerLoader;
import com.meti.lib.module.ModuleManager;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;

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

    public void start(Stage primaryStage) {
        console = new Console(Infinity.class.getSimpleName());
        console.log(Level.INFO, "Starting Infinity");

        try {
            properties = loadProperties();
            moduleManager = loadModules(properties);
            state = new InfinityState(
                    primaryStage,
                    properties,
                    console
            );

            loadMenu(primaryStage);
        } catch (Exception e) {
            console.log(Level.SEVERE, "Exception in starting Infinity", e);
        }
    }

    private ModuleManager loadModules(Properties properties) {
        String moduleDirectoryString = properties.getProperty("module_directory");

        if (moduleDirectoryString != null) {
            ModuleManager manager = new ModuleManager();
            manager.index(Paths.get(moduleDirectoryString));
            return manager;
        }
        else{
            throw new IllegalStateException("Cannot find module directory");
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
        properties.store(Files.newOutputStream(PROPERTIES_DEFAULT_PATH), null);
    }
}
