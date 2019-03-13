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
    private Server<InfinityClient, ServiceSubmitter> server;

    @FXML
    private ListView<String> clientListView;

    @FXML
    private TextArea output;

    @FXML
    private TextField input;

    public Server<InfinityClient, ServiceSubmitter> getServer() {
        if (server == null) {
            throw new IllegalStateException("Server has not been set");
        }

        return server;
    }

    public void handleCommand(String text) {
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
                log("Unknown command: " + text);
                break;
        }
    }

    @FXML
    public void handleInput() {
        String text = input.getText();
        input.setText(null);

        if (!text.startsWith("/")) {
            log(text);
        } else {
            handleCommand(text);
        }
    }

    public void log(String message) {
        output.appendText(message + "\n");
    }

    public void log(Exception exception) {
        StringWriter writer = new StringWriter();
        exception.printStackTrace(new PrintWriter(writer));
        exception.printStackTrace();
        log(writer.toString());
    }

    public void start(int port) {
        try {
            server = new InfinityServer(new ServerSocket(port), new ServiceSubmitter(service));
            service.submit(new SimpleFutureConsumer(server.listen()));

            log("Successfully started server on " + server.serverSocket.getLocalPort());
            log("Listening for clients at " + server.serverSocket.getInetAddress());
        } catch (IOException e) {
            log(e);
        }
    }

    public void stop() {
        try {
            getServer().close();

            service.shutdown();
            if (!service.isTerminated()) {
                List<Runnable> runnables = service.shutdownNow();
                log("Stopped server with " + runnables.size() + " tasks still running");
            }

            log("Successfully stopped server and disconnected clients");
        } catch (Exception e) {
            log(e);
        }
    }

    private class InfinityServer extends Server<InfinityClient, ServiceSubmitter> {
        public InfinityServer(ServerSocket serverSocket, ServiceSubmitter function) {
            super(serverSocket, function, InfinityClient.builder);
        }

        @Override
        public void handleClient(InfinityClient client) {
            log("Located client " + client.socket.getInetAddress());
            client.handlers.add(new Handler<>() {
                @Override
                public void accept(Object o) {
                    log(o.toString());
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
                log(e.get());
            } else {
                log("Server stopped successfully");
            }
        }
    }

    private class ServerConsole extends Console {

        @Override
        public void handle(String message) {
            output.appendText(message);

        }
    }
}
