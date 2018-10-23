package com.meti.app.server;

import com.meti.lib.client.ClientManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/21/2018
 */
public class InfinityServer {
    private final ExecutorService service = Executors.newCachedThreadPool();

    public final Logger logger = LoggerFactory.getLogger(InfinityServer.class);
    public final ClientManager clientManager;
    public final ServerSocket serverSocket;

    public InfinityServer(int port) throws IOException {
        this.clientManager = new ClientManager(new ClientConsumer());
        this.serverSocket = new ServerSocket(port);

        logger.info("Server constructed");
    }

    public void start() {
        logger.info("Server starting");
        service.submit(new ServerListener(this));
    }

    public boolean loop() {
        return true;
    }

    public void stop() throws InterruptedException {
        stop(1000);
    }

    public void stop(long milliseconds) throws InterruptedException {
        logger.info("Server stopping");

        logger.info("ExecutorService shutting down");
        service.shutdown();

        Thread.sleep(milliseconds);

        if(!service.isShutdown()){
            logger.warn("ExecutorService did not shut down cleanly, shutting down now!");
            service.shutdownNow();
        }
    }
}
