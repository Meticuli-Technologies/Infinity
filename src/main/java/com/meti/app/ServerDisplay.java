package com.meti.app;

import com.meti.lib.fx.BufferedConsole;
import com.meti.lib.fx.Controller;
import com.meti.lib.fx.PostInitializable;
import com.meti.lib.net.Server;
import com.meti.lib.util.Finalizable;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class ServerDisplay extends Controller implements Initializable, PostInitializable, Finalizable {
    @FXML
    private TextField inputField;

    @FXML
    private TextArea outputArea;

    private final InputParser inputParser = new InputParser();
    private BufferedConsole console;
    private AnimationTimer timer;
    private Set<Path> files;

    @FXML
    public void checkEnter(KeyEvent event){
        if(event.getCode().equals(KeyCode.ENTER)){
            inputParser.parseToken(inputField.getText());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        console = new BufferedConsole();
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                //TODO: could be a performance hiccup, but I'm not sure until I try it
                outputArea.setText(console.getBuffer().toString());
            }
        };

        timer.start();
    }

    @Override
    public void finalizeController() {
        timer.stop();
    }

    @Override
    public void postInitialize() {
        try {
            Server server = state.firstOfType(Server.class)
                    .orElseThrow((Supplier<Throwable>) () -> new IllegalStateException("Cannot find server to load in display"));

            console.log(Level.INFO, "Loaded server with port " + server.serverSocket.getLocalPort() + " at " + server.serverSocket.getInetAddress());

            loadProperties();
        } catch (Throwable throwable) {
            getLogger().error("", throwable);
        }
    }

    private void loadProperties() throws IOException {
        Optional<Properties> propertiesOptional = state.firstOfType(Properties.class);
        if (propertiesOptional.isPresent()) {
            Properties properties = propertiesOptional.get();
            String serverDirectoryName = getDirectoryName(properties);
            Path serverDirectory = Paths.get(".\\" + serverDirectoryName);

            files = loadServerDirectory(serverDirectory);
            printFiles(serverDirectory, files);
        } else {
            console.log(Level.WARNING, "Could not find properties");
        }
    }

    private void printFiles(Path serverDirectory, Set<Path> files) {
        StringBuilder builder = new StringBuilder();
        builder.append("Located ")
                .append(files.size())
                .append(" files in directory ")
                .append(serverDirectory.toAbsolutePath())
                .append(":");
        this.files.forEach(path -> {
            builder.append("\n\t");
            builder.append(path.toAbsolutePath());
        });

        console.log(Level.INFO, builder.toString());
    }

    private String getDirectoryName(Properties properties) {
        String serverDirectoryName;
        if (properties.containsKey("server_directory_name")) {
            serverDirectoryName = properties.getProperty("server_directory_name");
        } else {
            console.log(Level.WARNING, "Could not find server directory name, proceeding with default");
            serverDirectoryName = "content";
        }
        return serverDirectoryName;
    }

    private Set<Path> loadServerDirectory(Path serverDirectory) throws IOException {
        if (!Files.exists(serverDirectory)) {
            console.log(Level.WARNING, "Directory at " + serverDirectory.toAbsolutePath() + " was not found, server will create directory");

            Files.createDirectory(serverDirectory);
        }

        return Files.walk(serverDirectory).collect(Collectors.toSet());
    }

    public class InputParser {
        private final Map<Predicate<String>, Consumer<String[]>> inputMap = new HashMap<>();

        {
            inputMap.put(s -> s.startsWith("exit"), strings -> {
                getLogger().info("Exiting application");
                Platform.exit();
            });
        }

        void parseToken(String input){
            if (inputMap.size() != 0) {
                inputMap.keySet()
                        .stream()
                        .filter(stringPredicate -> stringPredicate.test(input))
                        .map(inputMap::get)
                        .forEach(consumer -> {
                            String[] parts = input.split(" ");
                            consumer.accept(Arrays.copyOfRange(parts, 1, parts.length));
                        });
            } else {
                throw new IllegalStateException("No values found inside of inputMap!");
            }
        }
    }
}
