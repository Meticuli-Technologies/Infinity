package com.meti.lib.fx;

import java.net.URL;
import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/1/2019
 */
public interface ExternalFXML<C extends Controller> extends Consumer<C> {
    default void acceptObject(Object obj) {
        accept(getControllerClass().cast(obj));
    }

    Class<C> getControllerClass();
    URL getURL();
}
