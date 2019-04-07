package com.meti.app;

import com.meti.lib.collect.State;
import com.meti.lib.fx.ControllerLoader;
import com.meti.lib.fx.FXMLBundle;
import com.meti.lib.log.ConsoleKey;
import com.meti.lib.log.LoggerConsole;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
class Infinity implements InfinityImpl {
    final LoggerConsole console = new LoggerConsole();
    final State state = new State();

    {
        console.eventManager.compound(ConsoleKey.ON_LOG, new ExitConsumer(Platform::exit));
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setScene(new Scene(getMenuBundle().parent));
            primaryStage.show();
        } catch (IOException e) {
            console.log(Level.SEVERE, e);
        }
    }

    @Override
    public void stop() {
    }

    FXMLBundle<?> getMenuBundle() throws IOException {
        return new ControllerLoader(state).getBundle(getMenuURL().openStream());
    }

    URL getMenuURL() {
        return getClass().getResource("/com/meti/app/Menu.fxml");
    }
}
