package com.meti.core;

import com.meti.control.ClientLoader;
import com.meti.control.Constructable;
import com.meti.control.Menu;
import com.meti.control.ServerLoader;
import com.meti.fx.Injector;
import com.meti.net.source.URLSource;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;

class InfinityStarter {
    private final InfinitySystem system;

    public InfinityStarter(InfinitySystem system) {
        this.system = system;
    }

    void startImpl(Stage primaryStage) throws IOException {
        system.getLogger().log(Level.INFO, "Starting application.");
        system.getStageManager().addStage(primaryStage);
        Menu menu = initMenu();
        ServerLoader serverLoader = menu.getModel().getServerLoader();
        ClientLoader clientLoader = menu.getModel().getClientLoader();
        setConsumers(Arrays.asList(serverLoader, clientLoader));
    }

    private List<Closeable> setConsumers(Collection<Constructable<? extends Closeable>> constructables) {
        List<Closeable> closeables = new ArrayList<>();
        constructables.forEach(constructable -> constructable.setOnConstructed(closeables::add));
        return closeables;
    }

    private Menu initMenu() throws IOException {
        Injector injector = new Injector(URLSource.of("/com/meti/Menu.fxml"), system);
        loadMenuDisplay(injector);
        return injector.getController();
    }

    private void loadMenuDisplay(Injector injector) throws IOException {
        Parent parent = injector.load();
        Stage stage = system.getStageManager().getPrimaryStage();
        stage.setScene(new Scene(parent));
        stage.show();
    }
}