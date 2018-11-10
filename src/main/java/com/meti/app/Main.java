package com.meti.app;

import com.meti.lib.fx.ControllerLoader;
import com.meti.lib.fx.ControllerState;
import com.meti.lib.net.Server;
import com.meti.lib.util.Finalizable;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class Main extends Application {
    private ControllerState state;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        state = new ControllerState(primaryStage, LoggerFactory.getLogger(Main.class));

        primaryStage.setScene(new Scene(ControllerLoader.load(getClass().getResource("/com/meti/app/Menu.fxml"), state)));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        Optional<Server> serverOptional = state.firstOfType(Server.class);
        if (serverOptional.isPresent()) {
            //TODO: server stop duration config file
            serverOptional.get().stop();
        }

        ControllerLoader.finalizables.forEach(Finalizable::finalizeController);
    }
}
