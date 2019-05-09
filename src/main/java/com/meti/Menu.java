package com.meti;

import javafx.fxml.FXML;

import java.io.IOException;
import java.net.InetAddress;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/7/2019
 */
public class Menu {
    private final Logger logger;
    private final ExecutorServiceManager executorServiceManager;

    private Consumer<Server> onServerConstructed;
    private Consumer<Client> onClientConstructed;

    public Menu(Logger logger, ExecutorServiceManager executorServiceManager) {
        this.logger = logger;
        this.executorServiceManager = executorServiceManager;
    }

    @FXML
    public void connect() {

    }

    @FXML
    public void host() {

    }

    @FXML
    public void local() {
        try {
            logger.log(Level.INFO, "Constructing server.");

            SocketSourceSupplier supplier = new SocketSourceSupplier();
            buildServer(supplier);
            Client client = buildClient(supplier.getLocalPort());
            buildClientHandler(client);
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    private void buildServer(SocketSourceSupplier supplier) {
        Server server = new Server(supplier, executorServiceManager, new InfinityServerHandler()).listen();
        if (onServerConstructed != null) {
            onServerConstructed.accept(server);
        } else {
            logger.log(Level.WARNING, "The server was constructed, but there were no consumers to accept it.");
        }
    }

    private Client buildClient(int localPort) throws IOException {
        Client client = new Client(new SocketSource(InetAddress.getByName("localhost"), localPort));
        if (onClientConstructed != null) {
            onClientConstructed.accept(client);
        } else {
            logger.log(Level.WARNING, "The client was constructed, but there were no consumers to accept it.");
        }
        return client;
    }

    private void buildClientHandler(Client client) {
        new ClientHandler(client, new InfinityClientTokenHandler()).listen(executorServiceManager);
    }

    public void setOnClientConstructed(Consumer<Client> onClientConstructed) {
        this.onClientConstructed = onClientConstructed;
    }

    public void setOnServerConstructed(Consumer<Server> onServerConstructed) {
        this.onServerConstructed = onServerConstructed;
    }
}
