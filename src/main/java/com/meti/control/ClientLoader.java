package com.meti.control;

import com.meti.concurrent.ExecutorServiceManager;
import com.meti.core.InfinitySystem;
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

    public void loadClient(int port, InetAddress address, InfinitySystem system, Stage stage) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Client client = construct(port, address);
        InfinityClientTokenHandler handler = buildClientHandler(client, system.getModuleManager(), system.getExecutorServiceManager());
        loadClientDisplay(system, client, handler, stage);
    }

    private Client construct(int localPort, InetAddress address) throws IOException {
        Client client = new Client(new SocketSource(address, localPort));
        Objects.requireNonNull(onClientConstructed).accept(client);
        return client;
    }

    private InfinityClientTokenHandler buildClientHandler(Client client, ModuleManager moduleManager, ExecutorServiceManager executorServiceManager) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        InfinityClientTokenHandler subHandler = new InfinityClientTokenHandler(moduleManager.constructInstances(ClientComponent.class));
        new ClientHandler(client, subHandler).listen(executorServiceManager);
        return subHandler;
    }

    private void loadClientDisplay(InfinitySystem system, Client client, InfinityClientTokenHandler handler, Stage stage) throws IOException {
        stage.setScene(new Scene(new Injector((URLSource.of("/com/meti/ClientDisplay.fxml")), system, client, handler).load()));
        stage.show();
    }

    @Override
    public void setOnConstructed(Consumer<Client> onClientConstructed) {
        this.onClientConstructed = onClientConstructed;
    }
}