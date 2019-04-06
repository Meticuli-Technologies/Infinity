package com.meti.lib.fx;

import javafx.application.Platform;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/6/2019
 */
public class FXUtil {
    private FXUtil(){
    }

    public static void throwIfNotFX(){
        if (!Platform.isFxApplicationThread()) {
            throw new IllegalStateException("Not on the JavaFX thread!");
        }
    }
}
