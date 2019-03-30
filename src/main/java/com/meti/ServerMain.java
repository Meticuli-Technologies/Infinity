package com.meti;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/30/2019
 */
public class ServerMain {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            int port = Integer.parseInt(scanner.nextLine());

            ExecutorService service = Executors.newCachedThreadPool();
            Server server = new Server(new ServerSocket(port), service);
            server.listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Server {
        private final ServerSocket serverSocket;
        private final ExecutorService service;

        private Server(ServerSocket serverSocket, ExecutorService service) {
            this.serverSocket = serverSocket;
            this.service = service;
        }

        public Future<Void> listen() {
            return service.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    while(!serverSocket.isClosed()){
                        serverSocket.accept();
                    }
                    return null;
                }
            });
        }
    }
}
