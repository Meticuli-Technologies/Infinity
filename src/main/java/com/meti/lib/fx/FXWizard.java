package com.meti.lib.fx;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/29/2019
 */
public abstract class FXWizard<T> extends AbstractWizard<T> {
    private final Stage stage = new Stage();
    private Parent root;

    @Override
    public void open() {
        Optional<Parent> rootOptional = getRoot();
        if (rootOptional.isPresent()) {
            super.open();

            stage.setScene(new Scene(rootOptional.get()));
            stage.show();
        } else {
            throw new IllegalStateException("Root has not been set!");
        }
    }

    public Optional<Parent> getRoot() {
        return Optional.ofNullable(root);
    }

    public Wizard<T> load(String name, Parent root) {
        Wizard<T> parent = load(name);
        this.root = root;
        return parent;
    }

    @Override
    public void close() {
        super.close();

        stage.close();
    }
}
