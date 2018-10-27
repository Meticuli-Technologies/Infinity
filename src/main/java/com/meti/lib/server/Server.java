package com.meti.lib.server;

import com.meti.lib.client.Client;
import com.meti.lib.client.ClientManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.meti.lib.server.ServerActionManager.RegexPredicate.of;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/21/2018
 */
public class Server {
    public final Logger logger = LoggerFactory.getLogger(Server.class);
    public final ClientManager clientManager;
    public ServerSocket serverSocket;
    private final ServerActionManager serverActionManager = new ServerActionManager();
    private final ExecutorService service = Executors.newCachedThreadPool();

    private final ServerInput serverIO = new ServerInput(System.in, System.out);

    private boolean shouldContinue = true;
    private Set<Path> files;

    {
        serverActionManager.put(of("stop"), s -> shouldContinue = false);

    }

    public Server() {
        this.clientManager = new ClientManager(new ClientConsumer(this));

        logger.info("Server constructed");
    }

    public void start() throws IOException {
        logger.info("Server starting");

        this.serverSocket = new ServerSocket(getPort(serverIO));
        this.service.submit(new ServerListener(this));
        this.service.submit(this::updateClients);

        indexFiles();
    }

    private int getPort(ServerInput serverIO) throws IOException {
        serverIO.writeLine("Enter in a port:");
        return Integer.parseInt(this.serverIO.readLine());
    }

    private Set<Path> indexFiles() throws IOException {
        Path contentDirectory = getContentDirectory();
        files = Files.walk(contentDirectory).collect(Collectors.toSet());
        logger.info("Loaded " + files.size() + " files:" + filesAsString(files));

        return files;
    }

    private Path getContentDirectory() throws IOException {
        Path directory = Paths.get(".\\content");
        if (!Files.exists(directory)) {
            Files.createDirectory(directory);
        }
        return directory;
    }

    private String filesAsString(Set<Path> files) {
        StringBuilder builder = new StringBuilder();
        files.stream().map(Path::toString).forEach(s -> builder.append("\t\n").append(s));
        return builder.toString();
    }

    public boolean loop() throws Exception {
        readLine(serverIO, serverActionManager);

        return shouldContinue;
    }

    private String readLine(ServerInput serverIO, ServerActionManager serverActionManager) throws IOException {
        String line;
        if ((line = serverIO.readLine()) != null) {
            serverActionManager.keySet()
                    .stream()
                    .filter(stringPredicate -> stringPredicate.test(line))
                    .map(serverActionManager::get)
                    .forEach(stringConsumer -> stringConsumer.accept(line));
        }
        return line;
    }

    public void stop() throws InterruptedException {
        stop(1000);
    }

    public void stop(long milliseconds) throws InterruptedException {
        logger.info("Server stopping");

        closeIO(serverIO);
        shutdownExecutorService(milliseconds, service);

        System.exit(0);
    }

    private void closeIO(ServerInput serverIO) {
        logger.info("Closing streams");

        try {
            serverIO.close();
        } catch (IOException e) {
            logger.error("Exception when closing streams", e);
        }
    }

    private void shutdownExecutorService(long milliseconds, ExecutorService service) throws InterruptedException {
        logger.info("ExecutorService shutting down");
        this.service.shutdown();

        Thread.sleep(milliseconds);

        if (!service.isShutdown()) {
            logger.warn("ExecutorService did not shut down cleanly, shutting down now!");
            this.service.shutdownNow();
        }
    }

    private Set<Client> updateClients() {
        return clientManager.values().stream()
                .filter(Client::isClosed)
                .map(client -> {
                    logger.info("Client " + client.socket.getInetAddress() + " has disconnected from the server");
                    return clientManager.remove(client.socket.getInetAddress());
                }).collect(Collectors.toSet());

    }

    public Optional<Set<Path>> getFiles() {
        return Optional.of(files);
    }
}
