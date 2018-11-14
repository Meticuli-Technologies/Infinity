package com.meti.app;

import com.meti.lib.util.BufferedConsole;
import com.meti.lib.fx.Controller;
import com.meti.lib.fx.PostInitializable;
import com.meti.lib.net.Client;
import com.meti.lib.net.Server;
import com.meti.lib.net.SocketConnection;
import com.meti.lib.util.Finalizable;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.nio.file.Path;
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
    private ListView<InetAddress> clientView;

    @FXML
    private TextField inputField;

    @FXML
    private TextArea outputArea;

    @FXML
    private TreeView<Path> fileView;

    private final InputParser inputParser = new InputParser();
    private BufferedConsole console;
    private AnimationTimer timer;

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

            console.log(Level.INFO, "Loaded ServerDisplay with port " + server.serverSocket.getLocalPort() + " at " + server.serverSocket.getInetAddress());

            loadServerDirectory(server);

            ObservableSet<Client<SocketConnection>> clientList = server.listener.clients;
            console.log(Level.INFO, writeClients(clientList));

            clientView.getItems()
                    .addAll(clientList.stream()
                            .map(socketConnectionClient -> socketConnectionClient.connection.socket.getInetAddress())
                            .collect(Collectors.toSet()));

            clientList.addListener((SetChangeListener<Client<SocketConnection>>) change -> {
                if(change.wasAdded()){
                    clientView.getItems().add(change.getElementAdded().connection.socket.getInetAddress());
                }
                if(change.wasRemoved()){
                    clientView.getItems().remove(change.getElementRemoved().connection.socket.getInetAddress());
                }
            });
        } catch (Throwable throwable) {
            getLogger().error("", throwable);
        }
    }

    public String writeClients(Set<Client<SocketConnection>> clients){
        StringBuilder builder = new StringBuilder("Located " + clients.size() + " clients:");
        clients.stream()
                .map(socketConnectionClient -> socketConnectionClient.connection.socket.getInetAddress())
                .forEach(inetAddress -> builder.append("\n\t").append(inetAddress.toString()));
        return builder.toString();
    }

    private void loadServerDirectory(Server server) throws IOException {
        String serverDirectoryName = getServerDirectoryName(server);
        Set<Path> files = loadProperties(server, serverDirectoryName);
        console.log(Level.INFO, server.printFiles());

        buildTree(server, files);
    }

    private void buildTree(Server server, Set<Path> files) {
        TreeItem<Path> root = new TreeItem<>(null);
        fileView.setShowRoot(false);
        fileView.setRoot(root);

        Map<Path, TreeItem<Path>> treeItemMap = new HashMap<>();
        files.forEach(path1 -> files.forEach(new TreeBuilder(treeItemMap, path1)));
        files.stream()
                .filter(path -> path.startsWith(server.getServerDirectory()) &&
                        path.getNameCount() == server.getServerDirectory().getNameCount())
                .forEach(path -> root.getChildren().add(treeItemMap.get(path)));
    }

    private Set<Path> loadProperties(Server server, String serverDirectoryName) throws IOException {
        boolean directoryCreated = server.createServerDirectory(serverDirectoryName);
        if (directoryCreated) {
            console.log(Level.WARNING, "Directory created at " + server.getServerDirectory().toAbsolutePath().toString());
        } else {
            console.log(Level.INFO, "Directory loaded at " + server.getServerDirectory().toAbsolutePath().toString());
        }

        return server.loadServerDirectory();
    }

    private String getServerDirectoryName(Server server) {
        Optional<String> directoryName = server.getDirectoryName(getProperties());
        String serverDirectoryName;
        if (directoryName.isPresent()) {
            serverDirectoryName = directoryName.get();
        } else {
            console.log(Level.WARNING, "No directory specified in properties found, using default directory");

            serverDirectoryName = directoryName.orElse(Server.DEFAULT_DIRECTORY_NAME);
        }
        return serverDirectoryName;
    }

    private static class TreeBuilder implements Consumer<Path> {
        private final Map<Path, TreeItem<Path>> treeItemMap;
        private final Path path1;

        public TreeBuilder(Map<Path, TreeItem<Path>> treeItemMap, Path path1) {
            this.treeItemMap = treeItemMap;
            this.path1 = path1;
        }

        @Override
        public void accept(Path path2) {
            if(!treeItemMap.containsKey(path1)){
                getTreeItem(path1);
            }

            if(!treeItemMap.containsKey(path2)){
                getTreeItem(path2);
            }

            if (!path1.equals(path2)) {
                if (isParent(path1, path2)) {
                    getTreeItem(path1).getChildren().add(getTreeItem(path2));
                }
            }
        }

        boolean isParent(Path parent, Path child) {
            return child.startsWith(parent);
        }

        TreeItem<Path> getTreeItem(Path path) {
            if (treeItemMap.containsKey(path)) {
                return treeItemMap.get(path);
            } else {
                TreeItem<Path> item = new TreeItem<>(path.getName(path.getNameCount() - 1));
                treeItemMap.put(path, item);
                return item;
            }
        }
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
