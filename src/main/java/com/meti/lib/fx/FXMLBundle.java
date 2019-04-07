package com.meti.lib.fx;

import javafx.scene.Parent;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/7/2019
 */
class FXMLBundle<T> {
    final Parent parent;
    final T controller;

    public FXMLBundle(Parent parent, T controller) {
        this.parent = parent;
        this.controller = controller;
    }
}
