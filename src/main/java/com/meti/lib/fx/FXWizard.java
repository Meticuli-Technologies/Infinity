package com.meti.lib.fx;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/29/2019
 */
public abstract class FXWizard<T> extends AbstractWizard<T> {
    private final Consumer<Parent> rootConsumer;
    private final Parent root;

    public FXWizard(String name, Parent root) {
        super(name);
        this.rootConsumer = new StageConsumer(this);
        this.root = root;
    }

    public FXWizard(String name, Consumer<Parent> rootConsumer, Parent root) {
        super(name);
        this.rootConsumer = rootConsumer;
        this.root = root;
    }

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

    public static class StageConsumer implements Consumer<Parent> {
        private final FXWizard<?> wizard;
        private Stage stage = new Stage();

        public StageConsumer(FXWizard<?> wizard) {
            this.wizard = wizard;
        }

        @Override
        public void accept(Parent parent) {
            stage.setScene(new Scene(parent));
            stage.show();

            stage.setOnCloseRequest(event -> wizard.close());
        }
    }
}
