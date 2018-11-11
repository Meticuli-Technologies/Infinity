package com.meti.lib.net;

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
import java.util.concurrent.Executors;
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
    private final BooleanProperty runningProperty = new SimpleBooleanProperty(true);
    private final ClientConsumer clientConsumer;
    public final ServerSocket serverSocket;
    private final ExecutorService service;

    private Future<Set<Client>> future;
    private Path serverDirectory;
    private Set<Path> files;

    public Path getServerDirectory() {
        return serverDirectory;
    }

    public Set<Path> getFiles() {
        return files;
    }

    public Server(int port, ClientConsumer clientConsumer) throws IOException {
        this.service = Executors.newCachedThreadPool();
        this.serverSocket = new ServerSocket(port);
        this.clientConsumer = clientConsumer;
    }

    public ServerListener start() {
        ServerListener listener = new ServerListener(clientConsumer, serverSocket);
        listener.runningProperty.bindBidirectional(runningProperty);
        future = service.submit(listener);
        return listener;
    }

    public Optional<Set<Client>> stop() throws Exception {
        return stop(Duration.ofSeconds(1));
    }

    public Optional<Set<Client>> stop(Duration duration) throws Exception {
        runningProperty.set(false);
        service.shutdown();

        serverSocket.close();

        try {
            return Optional.ofNullable(future.get(duration.toMillis(), TimeUnit.MILLISECONDS));
        } catch (Exception e) {
            /*
            even though we caught an exception, we should shutdown the service
            then send it back up to the user
            can't use finally because if the try block is successful
            the service will be shutdown properly and ExecutorService.shutdownNow() becomes redundant
            */
            service.shutdownNow();
            throw e;
        }
    }


    public boolean loadProperties(String serverDirectoryName) throws IOException {
        serverDirectory = Paths.get(".\\" + serverDirectoryName);
        boolean toReturn = true;

        if (!Files.exists(serverDirectory)) {
            Files.createDirectory(serverDirectory);
            toReturn = false;
        }

        files = loadServerDirectory(serverDirectory);
        return toReturn;
    }

    private Set<Path> loadServerDirectory(Path serverDirectory) throws IOException {
        return Files.walk(serverDirectory).collect(Collectors.toSet());
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
}
