package com.meti.core;

import com.meti.control.Menu;
import com.meti.fx.Injector;
import com.meti.net.source.URLSource;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;

public class InfinityStarter {
    private final Infinity infinity;

    public InfinityStarter(Infinity infinity) {
        this.infinity = infinity;
    }

    void startImpl(Stage primaryStage) throws IOException {
        infinity.getLogger().log(Level.INFO, "Starting application.");
        infinity.getStageManager().addStage(primaryStage);
        setConsumers(initMenu());
    }

    void setConsumers(Menu menu) {
        menu.getServerLoader().setOnConstructed(infinity.getCloseables()::add);
        menu.getClientLoader().setOnConstructed(infinity.getCloseables()::add);
    }

    Menu initMenu() throws IOException {
        Injector injector = new Injector(infinity.getLogger(), infinity.getExecutorServiceManager(), infinity.getStageManager(), infinity.getModuleManager());
        loadMenuDisplay(injector);
        return injector.getController();
    }

    void loadMenuDisplay(Injector injector) throws IOException {
        Parent parent = injector.load(URLSource.of("/com/meti/Menu.fxml"));
        Stage stage = infinity.getStageManager().getPrimaryStage();
        stage.setScene(new Scene(parent));
        stage.show();
    }
}