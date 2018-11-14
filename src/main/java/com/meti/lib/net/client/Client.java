package com.meti.lib.net.client;

import com.meti.lib.net.command.Command;
import com.meti.lib.net.connect.Connection;
import com.meti.lib.net.connect.ObjectConnection;

import java.io.Closeable;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

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

    public <R> Optional<R> runCommand(Command<?, ?> command) throws IOException {
        connection.objectOutputStream.writeObject(command);
        connection.objectOutputStream.flush();

        if(command.isReturning()){
            //TODO: return object
        }

        return Optional.empty();
    }

    @Override
    public void close() throws IOException {
        connection.close();
    }
}
