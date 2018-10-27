package com.meti.lib.client;

import com.meti.lib.server.command.Command;
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
import java.util.concurrent.TimeUnit;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/21/2018
 */
public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);
    private final ExecutorService clientService = Executors.newFixedThreadPool(2);

    public final Connection connection;
    public final ObjectInputStream inputStream;
    public final ObjectOutputStream outputStream;

    private final ObjectProperty<Duration> timeoutDuration = new SimpleObjectProperty<>();
    private boolean closed = false;

    {
        timeoutDuration.set(Duration.ofSeconds(1));
    }

    public static Client fromSocket(Socket socket) throws IOException {
        return new Client(new Connection(socket.getInputStream(), socket.getOutputStream()));
    }

    private Client(Connection connection) throws IOException {
        this.connection = connection;

        this.outputStream = new ObjectOutputStream(connection.outputStream);
        this.inputStream = new ObjectInputStream(connection.inputStream);
    }

    public <T extends Serializable> Optional<T> runCommand(Command<T> command) throws Exception {
        return runCommand(command, timeoutDuration.get());
    }

    public <T extends Serializable> Optional<T> runCommand(Command<T> command, Duration duration) throws Exception {
        Class<T> c = command.resultClass;

        logger.debug("Running command " + command + " with timeout of " + duration);

        writeCommand(outputStream, command);
        return command.isReceiving()
                ? receiveToken(clientService, inputStream, duration, c)
                : Optional.empty();
    }

    private <T extends Serializable> void writeCommand(ObjectOutputStream outputStream, Command<T> command) throws IOException {
        outputStream.writeObject(command);
        outputStream.flush();
    }

    private <T extends Serializable> Optional<T> receiveToken(ExecutorService clientService, ObjectInputStream inputStream, Duration duration, Class<T> tokenClass) throws Exception {
        Object token = getToken(clientService, inputStream, duration);
        if (tokenClass.isAssignableFrom(token.getClass())) {
            return Optional.of(tokenClass.cast(token));
        } else {
            throw new IllegalArgumentException("Server returned invalid response: " + token.toString());
        }
    }

    private Object getToken(ExecutorService clientService, ObjectInputStream inputStream, Duration duration) throws Exception {
        Future<?> future = clientService.submit(inputStream::readObject);
        Object token = future.get(duration.toMillis(), TimeUnit.MILLISECONDS);
        if (token instanceof Exception) {
            throw ((Exception) token);
        } else {
            return token;
        }
    }

    public boolean isClosed() {
        return closed;
    }

    public boolean close() throws IOException {
        if (!closed) {
            inputStream.close();
            outputStream.close();

            connection.close();

            return true;
        }
        else{
            return false;
        }
    }
}
