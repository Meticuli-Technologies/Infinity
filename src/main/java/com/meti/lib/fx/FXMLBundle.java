package com.meti.lib.fx;

import javafx.scene.Parent;

public class FXMLBundle<T> {
    private final Parent parent;
    private final T controller;

    public FXMLBundle(Parent parent, T controller) {
        this.parent = parent;
        this.controller = controller;
    }
}
