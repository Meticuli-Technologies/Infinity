package com.meti.lib.net.client;

import com.meti.lib.net.command.Command;
import com.meti.lib.net.command.ReturnableCommand;
import com.meti.lib.net.connect.ObjectConnection;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class Client<T extends ObjectConnection> implements Closeable  {
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(1);
    public final T connection;

    private ExecutorService service;

    public Client(T connection) {
        this(connection, Executors.newSingleThreadExecutor());
    }

    public Client(T connection, ExecutorService service) {
        this.connection = connection;
        this.service = service;
    }

    public <R> R runReturnableCommand(ReturnableCommand<?, ?, R> command) throws Exception {
        return runReturnableCommand(command, DEFAULT_TIMEOUT);
    }

    public <R> R runReturnableCommand(ReturnableCommand<?, ?, R> command, Duration timeout) throws Exception {
        return runReturnableCommand(command, service, timeout);
    }

    public <R> R runReturnableCommand(ReturnableCommand<?, ?, R> command, ExecutorService service, Duration timeout) throws Exception {
        runCommand(command);

        return parseFuture(command, service, timeout);
    }

    private <R> R parseFuture(ReturnableCommand<?, ?, R> command, ExecutorService service, Duration timeout) throws Exception {
        Future<?> future = service.submit(connection.objectInputStream::readObject);
        Object result = future.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
        return parseResult(result, command.returnClass);
    }

    private <R> R parseResult(Object result, Class<R> returnClass) {
        if(returnClass.isAssignableFrom(result.getClass())){
            return returnClass.cast(result);
        }
        else{
            throw new ClassCastException("Object returned was instance of " + result.getClass().getSimpleName() + " and not " + returnClass.getSimpleName());
        }
    }

    private void runCommand(Command<?, ?> command) throws Exception {
        connection.objectOutputStream.writeObject(command);
        connection.objectOutputStream.flush();
    }

    @Override
    public void close() throws IOException {
        connection.close();
    }
}
