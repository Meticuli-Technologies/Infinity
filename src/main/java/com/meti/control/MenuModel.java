package com.meti.control;

import com.meti.concurrent.ExecutorServiceManager;
import com.meti.fx.Injector;
import com.meti.fx.StageManager;
import com.meti.module.InfinityModuleManager;
import com.meti.net.source.SocketSourceSupplier;
import com.meti.net.source.URLSource;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.util.logging.Logger;

public class MenuModel {
    private final ClientLoader clientLoader = new ClientLoader();
    private final ServerLoader serverLoader = new ServerLoader();

    private final Logger logger;
    private final ExecutorServiceManager executorServiceManager;
    private final StageManager stageManager;
    private final InfinityModuleManager moduleManager;

    public MenuModel(Logger logger, ExecutorServiceManager executorServiceManager, StageManager stageManager, InfinityModuleManager moduleManager) {
        this.logger = logger;
        this.executorServiceManager = executorServiceManager;
        this.stageManager = stageManager;
        this.moduleManager = moduleManager;
    }

    public ClientLoader getClientLoader() {
        return clientLoader;
    }

    public ServerLoader getServerLoader() {
        return serverLoader;
    }

    void loadClient(int port) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        clientLoader.loadClient(port, InetAddress.getByName("localhost"), logger, executorServiceManager, stageManager, moduleManager, stageManager.allocate());
    }

    int loadServer() throws IOException {
        SocketSourceSupplier supplier = new SocketSourceSupplier();
        serverLoader.construct(supplier, executorServiceManager);
        loadServerDisplay(supplier);
        return supplier.getLocalPort();
    }

    private void loadServerDisplay(SocketSourceSupplier supplier) throws IOException {
        Stage serverStage = stageManager.allocate();
        serverStage.setScene(new Scene(new Injector(URLSource.of("/com/meti/ServerDisplay.fxml"), supplier).load()));
        serverStage.show();
    }
}