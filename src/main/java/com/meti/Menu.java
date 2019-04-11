package com.meti;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class Menu {
    @FXML
    private TextField portField;

    @FXML
    public void exit(){
        Platform.exit();
    }

    @FXML
    public void next(){
        try {
            int port = Integer.parseInt(portField.getText());
            ServerSocket serverSocket = new ServerSocket(port);

            ExecutorService service = Executors.newCachedThreadPool();
            service.submit((Callable<Void>) () -> {
                while (!serverSocket.isClosed()) {
                    Socket socket = serverSocket.accept();

                    Map<Predicate<Object>, Function<Object, Object>> handlers = new HashMap<>();
                    //TODO: add handlers

                    service.submit((Callable<Void>) () -> {
                        Querier.Client client = new Querier.Client(socket);

                        while (!socket.isClosed()) {
                            Object token = client.readObject();
                            Object toWrite;

                            Set<Object> results = handlers.keySet()
                                    .stream()
                                    .filter(predicate -> predicate.test(token))
                                    .map(handlers::get)
                                    .map(objectObjectFunction -> objectObjectFunction.apply(token))
                                    .collect(Collectors.toSet());

                            try {
                                toWrite = CollectionUtil.toSingle(results);
                            } catch (Exception e) {
                                toWrite = e;
                            }

                            client.writeObject(toWrite);
                            client.flush();
                        }
                        return null;
                    });
                }
                return null;
            });

            Socket socket = new Socket(InetAddress.getByName("localhost"), port);
            service.submit(new Querier(socket));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Querier implements Callable<Void> {
        private final BlockingQueue<CompletableFuture<Object>> futures = new ArrayBlockingQueue<>(16);
        private final Client client;

        public Querier(Socket socket) throws IOException {
            this.client = new Client(socket);
        }

        @Override
        public Void call() throws Exception {
            while (!client.getSocket().isClosed()) {
                Object token = client.readObject();
                if (token instanceof Throwable) {
                    futures.take().completeExceptionally((Throwable) token);
                } else {
                    futures.take().complete(token);
                }
            }
            return null;
        }

        public CompletableFuture<Object> query(Object input) throws IOException {
            client.writeObject(input);
            client.flush();

            CompletableFuture<Object> future = new CompletableFuture<>();
            futures.offer(future);
            return future;
        }

        public static class Client {
            final ObjectOutputStream objectOutputStream;
            final ObjectInputStream objectInputStream;
            private final Socket socket;

            public Client(Socket socket) throws IOException {
                this.socket = socket;
                this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                this.objectInputStream = new ObjectInputStream(socket.getInputStream());
            }

            public void flush() throws IOException {
                objectOutputStream.flush();
            }

            public Socket getSocket() {
                return socket;
            }

            public Object readObject() throws IOException, ClassNotFoundException {
                return objectInputStream.readObject();
            }

            public Object readUnshared() throws IOException, ClassNotFoundException {
                return objectInputStream.readUnshared();
            }

            public void writeObject(Object obj) throws IOException {
                objectOutputStream.writeObject(obj);
            }

            public void writeUnshared(Object obj) throws IOException {
                objectOutputStream.writeUnshared(obj);
            }
        }
    }
}
