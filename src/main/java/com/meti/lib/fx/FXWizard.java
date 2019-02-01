package com.meti.lib.fx;

import com.meti.lib.tuple.Tuple3;
import javafx.scene.Parent;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/29/2019
 */
public abstract class FXWizard<T> extends AbstractWizard<FXConsumer, T> {
    private final Parent root;

    public FXWizard(String name, Parent root) {
        super(name);

        this.root = root;
    }

    @Override
    @SafeVarargs
    public final void open(FXConsumer... handlers) {
        Optional<Parent> rootOptional = getRoot();

        FXConsumer handler = handlers[0];
        if (rootOptional.isPresent() && handler != null) {
            setRunning(true);
            handler.accept(new Tuple3<>());
        } else {
            if (!rootOptional.isPresent() && handler == null) {
                throw new IllegalStateException("Root and handler are unspecified");
            }

            if (!rootOptional.isPresent()) {
                throw new IllegalStateException("Root is unspecified");
            }

            throw new IllegalStateException("Handler is unspecified");
        }
    }

    public Optional<Parent> getRoot() {
        return Optional.ofNullable(root);
    }
}
