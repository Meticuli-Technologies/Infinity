package com.meti.lib.client;

import com.meti.app.server.command.Command;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/21/2018
 */
public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    public final ObjectInputStream inputStream;

    public final Socket socket;
    public final ObjectOutputStream outputStream;
    //TODO: make configurable
    private final ExecutorService clientService = Executors.newFixedThreadPool(2);

    private boolean closed = false;
    private final ObjectProperty<Duration> timeoutDuration = new SimpleObjectProperty<>();

    {
        timeoutDuration.set(Duration.ofSeconds(1));
    }

    public Client(Socket socket) throws IOException {
        this.socket = socket;

        //construction is reversed because the header would cause problems
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
    }

    public <T extends Serializable> Optional<T> runCommand(Command<T> command) throws Exception {
        return runCommand(command, timeoutDuration.get());
    }

    public <T extends Serializable> Optional<T> runCommand(Command<T> command, Duration duration) throws Exception {
        Class<T> c = command.resultClass;

        logger.debug("Running command " + command + " with timeout of " + duration);

        outputStream.writeObject(command);
        outputStream.flush();

        if (command.isReceiving()) {
            Future<?> future = clientService.submit(inputStream::readObject);

            //Object token = future.get(duration.toMillis(), TimeUnit.MILLISECONDS);
            Object token = future.get();
            if (token instanceof Exception) {
                throw ((Exception) token);
            }

            if (c.isAssignableFrom(token.getClass())) {
                return Optional.of(c.cast(token));
            } else {
                throw new IllegalArgumentException("Server returned invalid response: " + token.toString());
            }
        } else {
            return Optional.empty();
        }
    }

    public boolean isClosed() {
        return closed;
    }

    public boolean close() throws IOException {
        if (!closed) {
            inputStream.close();
            outputStream.close();

            socket.close();
            return true;
        }
        else{
            return false;
        }
    }
}
