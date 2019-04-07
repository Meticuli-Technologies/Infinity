package com.meti.lib.fx;

import com.meti.lib.collect.State;
import javafx.scene.layout.AnchorPane;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/7/2019
 */
class ControllerLoaderTest {

    @Test
    void construct() {
        State state = new State();
        ControllerLoader loader = new ControllerLoader(state);

        assertEquals(state, loader.state);
        assertEquals(ControllerCallback.class, loader.getControllerFactory().getClass());
    }

    @Test
    void load() throws IOException {
        State state = new State();
        String sampleFXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<?import javafx.scene.layout.AnchorPane?>\n" +
                "<AnchorPane xmlns=\"http://javafx.com/javafx\"\n" +
                "            xmlns:fx=\"http://javafx.com/fxml\"\n" +
                "            prefHeight=\"400.0\" prefWidth=\"600.0\">\n" +
                "</AnchorPane>\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(sampleFXML.getBytes());
        Object token = ControllerLoader.load(state, inputStream);
        assertEquals(AnchorPane.class, token.getClass());

        AnchorPane pane = (AnchorPane) token;
        assertEquals(600, pane.getPrefWidth());
        assertEquals(400, pane.getPrefHeight());
    }

    @Test
    void getBundle(){
        State state = new State();
        ControllerLoader loader = new ControllerLoader(state);

    /*    loader.getBundle();*/
    }
}
