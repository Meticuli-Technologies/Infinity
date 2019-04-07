package com.meti.lib.fx;

import javafx.scene.layout.AnchorPane;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/7/2019
 */
public class FXMLBundleTest {
    @Test
    void construct() {
        AnchorPane parent = new AnchorPane();
        String controller = "Controller";
        FXMLBundle<String> bundle = new FXMLBundle<>(parent, controller);

        assertEquals(parent, bundle.parent);
        assertEquals(controller, bundle.controller);
    }
}
