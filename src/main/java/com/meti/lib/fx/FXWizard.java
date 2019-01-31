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
    private final Consumer<Parent> handler;
    private final Parent root;

    public FXWizard(String name, Parent root) {
        super(name);

        this.handler = new StageConsumer();
        this.root = root;
    }

    public FXWizard(String name, Consumer<Parent> handler, Parent root) {
        super(name);

        this.handler = handler;
        this.root = root;
    }

    @Override
    public void open() {
        Optional<Parent> rootOptional = getRoot();
        Optional<Consumer<Parent>> handlerOptional = getHandler();

        if (rootOptional.isPresent() && handlerOptional.isPresent()) {
            super.open();
            handlerOptional.get().accept(rootOptional.get());
        } else {
            if (!rootOptional.isPresent() && !handlerOptional.isPresent()) {
                throw new IllegalStateException("Root and handler are unspecified");
            }

            if (!rootOptional.isPresent()) {
                throw new IllegalStateException("Root is unspecified");
            }

            throw new IllegalStateException("Handler is unspecified");
        }
    }

    public Optional<Consumer<Parent>> getHandler() {
        return Optional.ofNullable(handler);
    }

    public Optional<Parent> getRoot() {
        return Optional.ofNullable(root);
    }

    public class StageConsumer implements Consumer<Parent> {
        private Stage stage = new Stage();

        @Override
        public void accept(Parent parent) {
            stage.setOnCloseRequest(event -> close());
            stage.setScene(new Scene(parent));
            stage.showAndWait();
        }
    }
}
