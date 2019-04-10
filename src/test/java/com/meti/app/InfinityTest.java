package com.meti.app;

import com.meti.lib.collect.consume.CollectionConsumer;
import com.meti.lib.collect.tryable.TryableFactory;
import com.meti.lib.fx.FXUtil;
import com.meti.lib.util.ExceptionUtil;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/7/2019
 */
@ExtendWith(ApplicationExtension.class)
class InfinityTest {
    private final Infinity infinity = new Infinity();
    private Stage primaryStage;

    @Test
    void confirmStart() {
        assertNotNull(infinity.console);
        assertNotNull(infinity.factory);
        assertNotNull(infinity.state);
        assertTrue(primaryStage.isShowing());
    }

    @Test
    void getMenuURL() {
        URL url = URLS.getMenuURL();
        assertNotNull(url);
        assertTrue(url.getPath().endsWith("/com/meti/app/Menu.fxml"));
    }

    @Test
    void loadMenu() throws ExecutionException, InterruptedException {
        Stage stage = com.meti.lib.fx.FXUtil.call(Stage::new).get();
        Menu menu = FXUtil.call(() -> infinity.loadMenu(stage)).get();

        assertNotNull(menu);
        assertTrue(stage.isShowing());
    }

    @Start
    void onStart(Stage primaryStage) {
        this.primaryStage = primaryStage;
        infinity.start(primaryStage);
    }

    @Stop
    void onStop() {
        infinity.stop();
    }

    @Test
    void printStackTrace() {
        IllegalArgumentException e0 = new IllegalArgumentException();
        TryableFactory.DEFAULT.accept(() -> {
            throw e0;
        }).get();
        IllegalStateException e1 = new IllegalStateException();
        TryableFactory.DEFAULT.accept(() -> {
            throw e1;
        }).get();

        CollectionConsumer<String, ArrayList<String>> consumer = new CollectionConsumer<>(new ArrayList<>());
        infinity.printStackTrace(consumer);

        Collection<String> collection = consumer.collection;
        assertEquals(1, collection.size());
        assertEquals(ExceptionUtil.joinStackTrace(Stream.of(e0, e1)), consumer.collection.get(0));
    }
}
