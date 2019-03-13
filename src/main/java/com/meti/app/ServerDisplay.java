package com.meti.app;

import com.meti.lib.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/11/2019
 */
public class ServerDisplay {
    private final ExecutorService service = Executors.newCachedThreadPool();
    private final ServerConsole console = new ServerConsole();

    private Server<InfinityClient, ServiceSubmitter> server;

    @FXML
    private ListView<String> clientListView;

    @FXML
    private TextArea output;

    @FXML
    private TextField input;

    @FXML
    public void handleInput() {
        String text = input.getText();
        input.setText(null);

        if (!text.startsWith("/")) {
            log(Level.INFO, text);
        } else {
            try {
                handleCommand(text);
            } catch (IOException e) {
                log(Level.ERROR, "Failed to process command.", e);
            }
        }
    }

    public void handleCommand(String text) throws IOException {
        String[] args = text.substring(1).split(" ");
        switch (args[0]) {
            case "start":
                start(Integer.parseInt(args[1]));
                break;
            case "stop":
                stop();
                break;
            case "exit":
                stop();

                Platform.exit();
                break;
            default:
                log(Level.WARNING, "Unknown command: " + text);
                break;
        }
    }

    public void start(int port) throws IOException {
        server = new InfinityServer(new ServerSocket(port), new ServiceSubmitter(service));
        service.submit(new SimpleFutureConsumer(server.listen()));

        log(Level.INFO, "Successfully started server on " + server.serverSocket.getLocalPort());
        log(Level.INFO, "Listening for clients at " + server.serverSocket.getInetAddress());
    }

    public void stop() throws IOException {
            getServer().close();

            service.shutdown();
            if (!service.isTerminated()) {
                List<Runnable> runnables = service.shutdownNow();
                log(Level.WARNING, "Stopped server with " + runnables.size() + " tasks still running");
            }

            log(Level.INFO, "Successfully stopped server and disconnected clients");
    }

    public Server<InfinityClient, ServiceSubmitter> getServer() {
        if (server == null) {
            throw new IllegalStateException("Server has not been set");
        }

        return server;
    }

    public void log(Level level, String message) {
        console.log(User.ADMIN, level, message);
    }

    public void log(Level level, String message, Exception exception){
        console.log(User.ADMIN, level, message, exception);
    }

    public void log(Level level, Exception exception) {
        StringWriter writer = new StringWriter();
        exception.printStackTrace(new PrintWriter(writer));
        exception.printStackTrace();
        log(level, writer.toString());
    }

    private class InfinityServer extends Server<InfinityClient, ServiceSubmitter> {
        public InfinityServer(ServerSocket serverSocket, ServiceSubmitter function) {
            super(serverSocket, function, InfinityClient.builder);
        }

        @Override
        public void handleClient(InfinityClient client) {
            log(Level.INFO, "Located client " + client.socket.getInetAddress());
            client.handlers.add(new Handler<>() {
                @Override
                public void accept(Object o) {
                    log(Level.INFO, o.toString());
                }

                @Override
                public boolean test(Object o) {
                    return o instanceof String;
                }
            });

            clientListView.getItems().add(client.socket.getInetAddress().toString());

            Future<Optional<Exception>> submit = service.submit(client);
            service.submit(new SimpleFutureConsumer(submit));
        }
    }

    public class SimpleFutureConsumer extends FutureConsumer<Optional<Exception>> {
        public SimpleFutureConsumer(Future<Optional<Exception>> future) {
            super(future);
        }

        @Override
        public void accept(Optional<Exception> e) {
            if (e.isPresent()) {
                log(Level.INFO, e.get());
            } else {
                log(Level.INFO, "Server stopped successfully");
            }
        }
    }

    private class ServerConsole extends Console {
        @Override
        public void handle(User user, String message) {
            if (user.equals(User.ADMIN)) {
                output.appendText(message);
            }
        }
    }
}
