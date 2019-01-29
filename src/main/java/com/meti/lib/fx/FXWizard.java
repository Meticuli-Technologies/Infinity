package com.meti.lib.fx;

import javafx.scene.Parent;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/29/2019
 */
public abstract class FXWizard<T> extends AbstractWizard<T> {
    private Consumer<Parent> rootConsumer;
    private Parent root;

    @Override
    public void open() {
        Optional<Parent> rootOptional = getRoot();
        if (rootOptional.isPresent()) {
            super.open();

            Optional<Consumer<Parent>> consumerOptional = getRootConsumer();
            if (consumerOptional.isPresent()) {
                consumerOptional.get().accept(rootOptional.get());
            } else {
                throw new IllegalStateException("Consumer has not been set");
            }
        } else {
            throw new IllegalStateException("Root has not been set");
        }
    }

    public Optional<Consumer<Parent>> getRootConsumer() {
        return Optional.ofNullable(rootConsumer);
    }

    public Optional<Parent> getRoot() {
        return Optional.ofNullable(root);
    }

    public Wizard<T> load(String name, Parent root) {
        Wizard<T> parent = load(name);
        this.root = root;
        return parent;
    }
}
