package com.meti.control;

import com.meti.concurrent.ExecutorServiceManager;
import com.meti.fx.Injector;
import com.meti.module.ModuleManager;
import com.meti.net.client.Client;
import com.meti.net.client.ClientComponent;
import com.meti.net.client.ClientHandler;
import com.meti.net.client.InfinityClientTokenHandler;
import com.meti.net.source.SocketSourceSupplier;
import com.meti.net.source.URLSource;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

class MenuModel {
    private final Menu menu;

    MenuModel(Menu menu) {
        this.menu = menu;
    }

    void loadClient(int port) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Client client = menu.getClientLoader().construct(port);
        InfinityClientTokenHandler handler = buildClientHandler(client, menu.getModuleManager(), menu.getExecutorServiceManager());
        loadClientDisplay(client, handler);
    }

    private InfinityClientTokenHandler buildClientHandler(Client client, ModuleManager moduleManager, ExecutorServiceManager executorServiceManager) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        InfinityClientTokenHandler subHandler = new InfinityClientTokenHandler(moduleManager.constructInstances(ClientComponent.class));
        new ClientHandler(client, subHandler).listen(executorServiceManager);
        return subHandler;
    }

    private void loadClientDisplay(Client client, InfinityClientTokenHandler handler) throws IOException {
        Stage clientStage = menu.getStageManager().getStage(1);
        clientStage.setScene(Injector.loadAsScene(URLSource.of("/com/meti/ClientDisplay.fxml"), menu.getLogger(), menu.getStageManager(), menu.getModuleManager(), client, handler));
        clientStage.show();
    }

    int loadServer() throws IOException {
        SocketSourceSupplier supplier = new SocketSourceSupplier();
        menu.getServerLoader().construct(supplier, menu.getExecutorServiceManager());
        loadServerDisplay(supplier);
        return supplier.getLocalPort();
    }

    private void loadServerDisplay(SocketSourceSupplier supplier) throws IOException {
        Stage serverStage = menu.getStageManager().getPrimaryStage();
        serverStage.setScene(Injector.loadAsScene(URLSource.of("/com/meti/ServerDisplay.fxml"), supplier));
        serverStage.show();
    }
}