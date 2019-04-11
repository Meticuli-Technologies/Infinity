package com.meti;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

                            if (results.size() > 1) {
                                toWrite = new IllegalArgumentException("Has " + results.size() + " results, must return 1!");
                            } else if (results.isEmpty()) {
                                toWrite = new IllegalArgumentException("No handlers found.");
                            } else {
                                toWrite = new ArrayList<>(results).get(0);
                            }

                            objectOutputStream.writeObject(toWrite);
                            objectOutputStream.flush();
                        }
                        return null;
                    });
                }
                return null;
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
