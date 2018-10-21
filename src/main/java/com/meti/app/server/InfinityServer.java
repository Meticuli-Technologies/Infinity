package com.meti.app.server;

import com.meti.lib.client.Client;
import com.meti.lib.client.ClientManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/21/2018
 */
public class InfinityServer {
    private final ExecutorService service = Executors.newCachedThreadPool();
    private final ClientManager clientManager;
    private final ServerSocket serverSocket;

    public InfinityServer(int port) throws IOException {
        this.clientManager = new ClientManager(new ClientConsumer(), clientCallable, service);
        this.serverSocket = new ServerSocket(port);
    }

    public Future<Optional<Exception>> start() {
        return service.submit(new ServerListener());
    }

    public boolean loop() {
        return false;
    }

    public void stop() throws InterruptedException {
        stop(1000);
    }

    public void stop(long milliseconds) throws InterruptedException {
        service.shutdown();

        Thread.sleep(milliseconds);

        if(!service.isShutdown()){
            service.shutdownNow();
        }
    }

    private class ServerListener implements Callable<Optional<Exception>> {
        @Override
        public Optional<Exception> call() {
            try {
                Socket socket = serverSocket.accept();
                Client client = new Client(socket);

                clientManager.put(socket.getInetAddress(), client);

                return Optional.empty();
            } catch (IOException e) {
                return Optional.of(e);
            }
        }
    }

    private class ClientConsumer implements Consumer<Client> {
        @Override
        public void accept(Client client) {
            //TODO: server-side code
        }
    }
}
