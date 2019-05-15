package com.meti.core;

import com.meti.control.Menu;
import com.meti.fx.Injector;
import com.meti.net.source.URLSource;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;

class InfinityStarter {
    private final Infinity infinity;

    public InfinityStarter(Infinity infinity) {
        this.infinity = infinity;
    }

    void startImpl(Stage primaryStage) throws IOException {
        infinity.getLogger().log(Level.INFO, "Starting application.");
        infinity.getStageManager().addStage(primaryStage);
        setConsumers(initMenu());
    }

    private void setConsumers(Menu menu) {
        menu.getModel().getServerLoader().setOnConstructed(infinity.getCloseables()::add);
        menu.getModel().getClientLoader().setOnConstructed(infinity.getCloseables()::add);
    }

    private Menu initMenu() throws IOException {
        Injector injector = new Injector(URLSource.of("/com/meti/Menu.fxml"), infinity.getLogger(), infinity.getExecutorServiceManager(), infinity.getStageManager(), infinity.getModuleManager());
        loadMenuDisplay(injector);
        return injector.getController();
    }

    private void loadMenuDisplay(Injector injector) throws IOException {
        Parent parent = injector.load();
        Stage stage = infinity.getStageManager().getPrimaryStage();
        stage.setScene(new Scene(parent));
        stage.show();
    }
}