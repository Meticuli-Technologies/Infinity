package com.meti.lib.net.client;

import com.meti.lib.net.command.Command;
import com.meti.lib.net.command.ReturnableCommand;
import com.meti.lib.net.connect.ObjectConnection;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class Client<T extends ObjectConnection> implements Closeable  {
    public final T connection;

    public Client(T connection) {
        this.connection = connection;
    }

    public <R> R runReturnableCommand(ReturnableCommand<?, ?, R> command, ExecutorService service, Duration timeout) throws Exception {
        Future<?> future = service.submit(connection.objectInputStream::readObject);
        Object result = future.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
        Class<R> returnClass = command.returnClass;
        if(returnClass.isAssignableFrom(result.getClass())){
            return returnClass.cast(result);
        }
        else{
            throw new ClassCastException("Object returned was instance of " + result.getClass().getSimpleName() + " and not " + returnClass.getSimpleName());
        }
    }

    public void runCommand(Command<?, ?> command) throws Exception {
        connection.objectOutputStream.writeObject(command);
        connection.objectOutputStream.flush();
    }

    @Override
    public void close() throws IOException {
        connection.close();
    }
}
