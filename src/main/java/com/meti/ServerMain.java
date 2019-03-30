package com.meti;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

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
            Server server = new Server(new ServerSocket(port));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Server {
        private final ServerSocket serverSocket;

        private Server(ServerSocket serverSocket) {
            this.serverSocket = serverSocket;
        }
    }
}
