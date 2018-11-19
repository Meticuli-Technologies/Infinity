package com.meti.app;

import com.meti.lib.console.BufferedConsole;
import com.meti.lib.fx.Controller;
import com.meti.lib.fx.Finalizable;
import com.meti.lib.fx.PostInitializable;
import com.meti.lib.fx.TreeBuilder;
import com.meti.lib.net.client.Client;
import com.meti.lib.net.connect.SocketConnection;
import com.meti.lib.net.server.Server;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.util.logging.Level;

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
                if (console.wasUpdated()) {
                    outputArea.setText(console.getWriter().toString());
                }
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
            Server server = state.getServer();
            console.log(Level.INFO, "Loaded ServerDisplay with port " + server.serverSocket.getLocalPort() + " at " + server.serverSocket.getInetAddress());

            loadServerDirectory(server);
            createClientView(server.listener.clients);

            inputParser.state = state;
        } catch (Exception exception) {
            state.getLogger(ServerDisplay.this.getClass()).error("", exception);
        }
    }

    private void createClientView(ObservableSet<Client<SocketConnection>> clientList) {
        clientList.addListener((SetChangeListener<Client<SocketConnection>>) change -> {
            if (change.wasAdded()) {
                clientView.getItems().add(change.getElementAdded().connection.socket.getInetAddress());
            }
            if (change.wasRemoved()) {
                clientView.getItems().remove(change.getElementRemoved().connection.socket.getInetAddress());
            }
        });
    }

    private void loadServerDirectory(Server server) throws IOException {
        String serverDirectoryName = getServerDirectoryName(server);
        Set<Path> files = loadServerDirectory(server, serverDirectoryName);
        console.log(Level.INFO, server.printFiles());

        buildTree(files);
    }

    private void buildTree(Set<Path> files) {
        TreeItem<Path> root = new PathTreeBuilder().buildTree(files);
        fileView.setShowRoot(false);
        fileView.setRoot(root);
    }

    private Set<Path> loadServerDirectory(Server server, String serverDirectoryName) throws IOException {
        boolean directoryCreated = server.createServerDirectory(serverDirectoryName);
        if (directoryCreated) {
            console.log(Level.WARNING, "Directory created at " + server.getFileDirectory().toAbsolutePath().toString());
        } else {
            console.log(Level.INFO, "Directory loaded at " + server.getFileDirectory().toAbsolutePath().toString());
        }

        return server.loadServerDirectory();
    }

    private String getServerDirectoryName(Server server) {
        Optional<String> directoryName = server.getDirectoryName(state.getProperties());
        String serverDirectoryName;
        if (directoryName.isPresent()) {
            serverDirectoryName = directoryName.get();
        } else {
            console.log(Level.WARNING, "No directory specified in properties found, using default directory");

            serverDirectoryName = directoryName.orElse(Server.DEFAULT_DIRECTORY_NAME);
        }
        return serverDirectoryName;
    }

    private static class PathTreeBuilder extends TreeBuilder<Path> {

        @Override
        public TreeItem<Path> detectParent(Path parent, Path child) {
            return createTreeItem(child);
        }

        @Override
        public boolean isParent(Path parent, Path child) {
            return child.startsWith(parent);
        }

        @Override
        public TreeItem<Path> createTreeItem(Path value) {
            return new TreeItem<>(value.getName(value.getNameCount() - 1));
        }
    }
}
