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
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

                        while (!socket.isClosed()) {
                            Object token = objectInputStream.readObject();
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

                            objectOutputStream.writeObject(toWrite);
                            objectOutputStream.flush();
                        }
                        return null;
                    });
                }
                return null;
            });

            Socket socket = new Socket(InetAddress.getByName("localhost"), port);
            service.submit(new VoidCallable(socket));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class VoidCallable implements Callable<Void> {
        private final BlockingQueue<CompletableFuture<Object>> futures = new ArrayBlockingQueue<>(16);
        private final Socket socket;
        private final ObjectOutputStream objectOutputStream;
        private final ObjectInputStream objectInputStream;

        public VoidCallable(Socket socket) throws IOException {
            this.socket = socket;

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        }

        @Override
        public Void call() throws Exception {
            while (!socket.isClosed()) {
                Object token = objectInputStream.readObject();
                if (token instanceof Throwable) {
                    futures.take().completeExceptionally((Throwable) token);
                } else {
                    futures.take().complete(token);
                }
            }
            return null;
        }

        public CompletableFuture<Object> query(Object input) throws IOException {
            objectOutputStream.writeObject(input);
            objectOutputStream.flush();

            CompletableFuture<Object> future = new CompletableFuture<>();
            futures.offer(future);
            return future;
        }
    }
}
