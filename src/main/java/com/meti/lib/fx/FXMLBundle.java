package com.meti.lib.fx;

import javafx.scene.Parent;

import java.util.function.Consumer;

public class FXMLBundle<T> {
    private final Parent root;
    private final T controller;

    public FXMLBundle(Parent root, T controller) {
        this.root = root;
        this.controller = controller;
    }

    public FXMLBundle<T> initController(Consumer<T> controllerConsumer) {
        controllerConsumer.accept(controller);
        return this;
    }

    public FXMLBundle<T> initRoot(Consumer<Parent> rootConsumer) {
        rootConsumer.accept(root);
        return this;
    }
}
