package com.meti.app;

import com.meti.lib.collect.State;
import com.meti.lib.collect.catches.Catcher;
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
import java.io.InputStream;
import java.net.URL;
import java.util.function.Consumer;
import java.util.logging.Level;

import static com.meti.lib.util.ExceptionUtil.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
class Infinity implements InfinityImpl {
    final LoggerConsole console = new LoggerConsole();
    final TryableFactory<Catcher> factory;
    final State state;

    {
        factory = new TryableFactory<>(console.asCatcher(Level.SEVERE));
        console.eventManager.compound(ConsoleKey.ON_LOG, new ExitConsumer(Platform::exit));

        state = new State(console, factory);
    }

    @Override
    public void start(Stage primaryStage) {
        OptionalUtil.throwIfPresent(factory.accept(() -> loadMenu(primaryStage)).get());
    }

    Menu loadMenu(Stage primaryStage) throws IOException {
        InputStream inputStream = getMenuURL()
                .openStream();
        FXMLBundle<?> bundle = new ControllerLoader(state, primaryStage)
                .getBundle(inputStream);

        primaryStage.setScene(new Scene(bundle.parent));
        primaryStage.show();
        return (Menu) bundle.controller;
    }

    URL getMenuURL() {
        return getClass().getResource("/com/meti/app/Menu.fxml");
    }

    @Override
    public void stop() {
        printStackTrace(exceptionString -> console.log(Level.SEVERE, exceptionString));
    }

    public void printStackTrace(Consumer<String> consumer) {
        String exceptionString = joinStackTrace(TryableFactory.DEFAULT.catcher.collection.stream());
        if (exceptionString.length() != 0) {
            consumer.accept(exceptionString);
        }
    }
}
