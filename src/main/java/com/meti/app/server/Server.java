package com.meti.app.server;

import com.meti.lib.client.Client;
import com.meti.lib.client.ClientManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.meti.app.server.ServerActionManager.RegexPredicate.of;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/21/2018
 */
public class Server {
    public final Logger logger = LoggerFactory.getLogger(Server.class);
    public final ClientManager clientManager;
    public final ServerSocket serverSocket;
    private final ServerActionManager serverActionManager = new ServerActionManager();
    private final ExecutorService service = Executors.newCachedThreadPool();
    private final ServerInput serverInput = new ServerInput(System.in, System.out);
    private boolean shouldContinue = true;

    {
        serverActionManager.put(of("stop"), s -> shouldContinue = false);

    }

    public Server(int port) throws IOException {
        this.clientManager = new ClientManager(new ClientConsumer());
        this.serverSocket = new ServerSocket(port);

        logger.info("Server constructed");
    }

    public void start() {
        logger.info("Server starting");
        service.submit(new ServerListener(this));

        try {
            Path directory = Paths.get(".\\content");
            if (!Files.exists(directory)) {
                Files.createDirectory(directory);
            }

            StringBuilder builder = new StringBuilder();
            Set<Path> walkedPaths = Files.walk(directory).collect(Collectors.toSet());
            walkedPaths.stream().map(Path::toString).forEach(s -> builder.append("\t\n").append(s));

            logger.info("Loaded " + walkedPaths.size() + " files:" + builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean loop() throws Exception {
        String line;
        if((line = serverInput.readLine()) != null){
            serverActionManager.keySet()
                    .stream()
                    .filter(stringPredicate -> stringPredicate.test(line))
                    .map(serverActionManager::get)
                    .forEach(stringConsumer -> stringConsumer.accept(line));
        }

        clientManager.values().stream()
                .filter(Client::isClosed)
                .forEach(client -> {
                    logger.info("Client " + client.socket.getInetAddress() + " has disconnected from the server");
                    clientManager.remove(client.socket.getInetAddress());
                });

        return shouldContinue;
    }

    public void stop() throws InterruptedException {
        stop(1000);
    }

    public void stop(long milliseconds) throws InterruptedException {
        logger.info("Server stopping");

        logger.info("Closing streams");
        try {
            serverInput.close();
        } catch (IOException e) {
            logger.error("Exception when closing streams", e);
        }

        logger.info("ExecutorService shutting down");
        service.shutdown();

        Thread.sleep(milliseconds);

        if (!service.isShutdown()) {
            logger.warn("ExecutorService did not shut down cleanly, shutting down now!");
            service.shutdownNow();
        }

        System.exit(0);
    }
}
