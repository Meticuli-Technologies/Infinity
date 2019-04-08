package com.meti.app;

import com.meti.lib.collect.State;
import com.meti.lib.collect.tryable.TryableFactory;
import com.meti.lib.fx.ControllerLoader;
import com.meti.lib.fx.FXMLBundle;
import com.meti.lib.log.ConsoleKey;
import com.meti.lib.log.LoggerConsole;
import com.meti.lib.util.OptionalUtil;
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
    final TryableFactory factory;
    final State state;

    {
        factory = new TryableFactory(console.asCatcher(Level.SEVERE));
        console.eventManager.compound(ConsoleKey.ON_LOG, new ExitConsumer(Platform::exit));

        state = new State(console, factory);
    }

    @Override
    public void start(Stage primaryStage) {
        OptionalUtil.throwIfPresent(factory.perform(() -> {
            loadMenu(primaryStage);
        }).get());
    }

    Menu loadMenu(Stage primaryStage) throws IOException {
        FXMLBundle<?> bundle = getMenuBundle();
        primaryStage.setScene(new Scene(bundle.parent));
        primaryStage.show();
        return (Menu) bundle.controller;
    }

    FXMLBundle<?> getMenuBundle() throws IOException {
        return new ControllerLoader(state).getBundle(getMenuURL().openStream());
    }

    static URL getMenuURL() {
        return Infinity.class.getResource("/com/meti/app/Menu.fxml");
    }

    @Override
    public void stop() {
    }
}
