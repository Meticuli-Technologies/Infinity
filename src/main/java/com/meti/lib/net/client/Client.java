package com.meti.lib.net.client;

import com.meti.lib.collect.ClassMap;
import com.meti.lib.convert.ClassConverter;
import com.meti.lib.convert.ConsumerConverter;
import com.meti.lib.net.command.Command;
import com.meti.lib.net.command.Result;
import com.meti.lib.net.command.ReturnableCommand;
import com.meti.lib.net.connect.ObjectConnection;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class Client<T extends ObjectConnection> implements Closeable {
    public final T connection;

    private final BooleanProperty running = new SimpleBooleanProperty(true);
    public ClientInput input;
    public ClientOutput output;
    private ResultConsumerConverter resultConsumerConverter;

    public Client(T connection) {
        this.connection = connection;
    }

    @Override
    public void close() throws IOException {
        connection.close();
    }

    public void listen(ExecutorService service) {
        service.submit(input = new ClientInput());
        service.submit(output = new ClientOutput());

        resultConsumerConverter = new ResultConsumerConverter();
        input.getList(Result.class).addListener((ListChangeListener<Object>) c -> c.getAddedSubList().forEach(resultConsumerConverter));
    }

    public <R> R runReturnableCommand(ReturnableCommand<?, ?, R> command) throws Exception {
        runCommand(command);

        return parseFuture(command);
    }

    private void runCommand(Command<?, ?> command) {
        output.outputQueue.add(command);
    }

    private <R> R parseFuture(ReturnableCommand<?, ?, R> command) throws Exception {
        resultConsumerConverter.await(command.returnClass, Duration.ofSeconds(2));
        Optional<R> result = resultConsumerConverter.classMap.firstOfType(command.returnClass);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new IOException("No result found");
        }
    }

    private static class ResultConsumerConverter extends ConsumerConverter<Result> {
        public final ClassMap classMap = new ClassMap();

        public ResultConsumerConverter() {
            super(new ClassConverter<>(Result.class));
        }

        @Override
        public void acceptT(Result object) {
            classMap.addObject(object.value);
        }

        public <R> R await(Class<R> rClass, Duration duration) throws InterruptedException {
            if (!classMap.containsClass(rClass)) {
                Thread.sleep(duration.toMillis());
            }

            Optional<R> optional = classMap.firstOfType(rClass);
            if (optional.isPresent()) {
                return optional.get();
            } else {
                throw new IllegalStateException("Cannot find class " + rClass.getSimpleName());
            }
        }
    }

    public class ClientInput implements Callable<Optional<Exception>> {
        public final ObservableMap<Class<?>, ObservableList<Object>> classListMap = FXCollections.observableMap(new HashMap<>());

        @Override
        public Optional<Exception> call() {
            try {
                while (running.get()) {
                    Object input = connection.objectInputStream.readObject();
                    System.out.println(input);

                    Class<?> inputClass = input.getClass();
                    if (!classListMap.containsKey(inputClass)) {
                        classListMap.put(inputClass, FXCollections.observableList(new ArrayList<>()));
                    }
                    classListMap.get(inputClass).add(input);
                }

                return Optional.empty();
            } catch (Exception e) {
                return Optional.of(e);
            }
        }

        public ObservableList<Object> getList(Class<?> c) {
            if (!classListMap.containsKey(c)) {
                classListMap.put(c, FXCollections.observableList(new ArrayList<>()));
            }
            return classListMap.get(c);
        }
    }

    public class ClientOutput implements Callable<Optional<IOException>> {
        public final Queue<Serializable> outputQueue = new PriorityQueue<>();

        @Override
        public Optional<IOException> call() {
            try {
                while (running.get()) {
                    if (outputQueue.size() > 0) {
                        while (outputQueue.size() > 0) {
                            Serializable object = outputQueue.poll();
                            connection.objectOutputStream.writeObject(object);
                        }

                        connection.objectOutputStream.flush();
                    }
                }

                return Optional.empty();
            } catch (IOException e) {
                return Optional.of(e);
            }
        }
    }
}
