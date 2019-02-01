package com.meti.lib.util;

import javafx.scene.Node;

import static javafx.scene.layout.AnchorPane.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/1/2019
 */
public class FXUtil {
    private FXUtil() {
    }

    public static void zeroAnchors(Node root) {
        setTopAnchor(root, 0d);
        setBottomAnchor(root, 0d);
        setLeftAnchor(root, 0d);
        setRightAnchor(root, 0d);
    }
}
