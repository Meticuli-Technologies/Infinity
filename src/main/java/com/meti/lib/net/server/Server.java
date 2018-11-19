package com.meti.lib.net.server;

import com.meti.lib.net.client.Client;
import com.meti.lib.net.client.ClientConsumer;
import com.meti.lib.net.connect.SocketConnection;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class Server {
    public static final String DEFAULT_DIRECTORY_NAME = "content";

    public final BooleanProperty runningProperty = new SimpleBooleanProperty(true);
    public final ServerSocket serverSocket;

    public ServerListener listener;

    private ClientConsumer<SocketConnection> clientConsumer;
    private Future<Optional<Set<Client<SocketConnection>>>> future;
    private Path serverDirectory;
    private Set<Path> files;

    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public Server(int port, ClientConsumer<SocketConnection> consumer) throws IOException {
        this(port);
        this.clientConsumer = consumer;
        this.clientConsumer.setServer(this);
    }

    public ServerListener start(ExecutorService service) {
        listener = new ServerListener(clientConsumer, serverSocket, service);
        listener.runningProperty.bindBidirectional(runningProperty);
        future = service.submit(listener);
        return listener;
    }

    public Optional<Set<Client<SocketConnection>>> stop() throws Exception {
        return stop(Duration.ofSeconds(1));
    }

    public Optional<Set<Client<SocketConnection>>> stop(Duration duration) throws Exception {
        runningProperty.set(false);
        serverSocket.close();

        for (Client<SocketConnection> socketConnectionClient : listener.clients) {
            socketConnectionClient.close();
        }

        return future.get(duration.toMillis(), TimeUnit.MILLISECONDS);
    }

    public boolean createServerDirectory(String serverDirectoryName) throws IOException {
        serverDirectory = Paths.get(".\\" + serverDirectoryName);
        boolean toReturn = true;

        if (!Files.exists(serverDirectory)) {
            Files.createDirectory(serverDirectory);
            toReturn = false;
        }

        return toReturn;
    }

    public Set<Path> loadServerDirectory() throws IOException {
        return files = Files.walk(serverDirectory).collect(Collectors.toSet());
    }

    public String printFiles() {
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

        return builder.toString();
    }

    public Optional<String> getDirectoryName(Properties properties) {
        if (properties.containsKey("server_directory_name")) {
            return Optional.of(properties.getProperty("server_directory_name"));
        } else {
            return Optional.empty();
        }
    }

    public Path getFileDirectory() {
        return serverDirectory;
    }

    public Set<Path> getFiles() {
        return files;
    }
}
