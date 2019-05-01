package com.meti.lib.fx.fxml;

import javafx.scene.Parent;

public class FXMLBundle<T> {
    public final Parent root;
    public final T controller;

    public FXMLBundle(Parent root, T controller) {
        this.root = root;
        this.controller = controller;
    }
}
