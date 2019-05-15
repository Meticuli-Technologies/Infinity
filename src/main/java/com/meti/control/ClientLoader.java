package com.meti.control;

import com.meti.concurrent.ExecutorServiceManager;
import com.meti.fx.Injector;
import com.meti.fx.StageManager;
import com.meti.module.ModuleManager;
import com.meti.net.client.Client;
import com.meti.net.client.ClientComponent;
import com.meti.net.client.ClientHandler;
import com.meti.net.client.InfinityClientTokenHandler;
import com.meti.net.source.SocketSource;
import com.meti.net.source.URLSource;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class ClientLoader implements Constructable<Client> {
    private Consumer<Client> onClientConstructed;

    void loadClient(int port, ModuleManager moduleManager, ExecutorServiceManager executorServiceManager, StageManager stageManager, Logger logger, Stage stage) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Client client = construct(port);
        InfinityClientTokenHandler handler = buildClientHandler(client, moduleManager, executorServiceManager);
        loadClientDisplay(stageManager, logger, executorServiceManager, moduleManager, client, handler, stage);
    }

    private Client construct(int localPort) throws IOException {
        Client client = new Client(new SocketSource(InetAddress.getByName("localhost"), localPort));
        Objects.requireNonNull(onClientConstructed).accept(client);
        return client;
    }

    private InfinityClientTokenHandler buildClientHandler(Client client, ModuleManager moduleManager, ExecutorServiceManager executorServiceManager) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        InfinityClientTokenHandler subHandler = new InfinityClientTokenHandler(moduleManager.constructInstances(ClientComponent.class));
        new ClientHandler(client, subHandler).listen(executorServiceManager);
        return subHandler;
    }

    private void loadClientDisplay(StageManager stageManager, Logger logger, ExecutorServiceManager executorServiceManager, ModuleManager moduleManager, Client client, InfinityClientTokenHandler handler, Stage stage) throws IOException {
        stage.setScene(new Scene(new Injector((URLSource.of("/com/meti/ClientDisplay.fxml")), logger, executorServiceManager, stageManager, moduleManager, client, handler).load()));
        stage.show();
    }

    @Override
    public void setOnConstructed(Consumer<Client> onClientConstructed) {
        this.onClientConstructed = onClientConstructed;
    }
}