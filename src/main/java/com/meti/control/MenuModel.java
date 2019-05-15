package com.meti.control;

import com.meti.core.InfinitySystem;
import com.meti.fx.Injector;
import com.meti.net.source.SocketSourceSupplier;
import com.meti.net.source.URLSource;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;

public class MenuModel {
    private final ClientLoader clientLoader = new ClientLoader();
    private final ServerLoader serverLoader = new ServerLoader();

    private final InfinitySystem system;

    public MenuModel(InfinitySystem system) {
        this.system = system;
    }

    public ClientLoader getClientLoader() {
        return clientLoader;
    }

    public ServerLoader getServerLoader() {
        return serverLoader;
    }

    void loadClient(int port) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        clientLoader.loadClient(port, InetAddress.getByName("localhost"), system, system.getStageManager().allocate());
    }

    int loadServer() throws IOException {
        SocketSourceSupplier supplier = new SocketSourceSupplier();
        serverLoader.construct(supplier, system.getExecutorServiceManager());
        loadServerDisplay(supplier);
        return supplier.getLocalPort();
    }

    private void loadServerDisplay(SocketSourceSupplier supplier) throws IOException {
        Stage serverStage = system.getStageManager().allocate();
        serverStage.setScene(new Scene(new Injector(URLSource.of("/com/meti/ServerDisplay.fxml"), supplier).load()));
        serverStage.show();
    }
}