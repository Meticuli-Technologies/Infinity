package com.meti.lib.fx;

import com.meti.lib.state.State;
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
public abstract class FXWizard<T> extends AbstractWizard<Consumer<Parent>, T> {
    private final State state;
    private final Parent root;

    public FXWizard(String name, State state, Parent root) {
        super(name);

        this.state = state;
        this.root = root;
    }

    @Override
    public void open(Consumer<Parent>... handlers) {
        Optional<Parent> rootOptional = getRoot();

        Consumer<Parent> handler = handlers[0];
        if (rootOptional.isPresent() && handler != null) {
            setRunning(true);
            handler.accept(rootOptional.get());
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

    public class StageConsumer implements Consumer<Parent> {
        private final Stage stage;

        public StageConsumer() {
            stage = state.singleContent(StageManager.class).allocate();
        }

        @Override
        public void accept(Parent parent) {
            stage.setOnCloseRequest(event -> close());
            stage.setScene(new Scene(parent));
            stage.showAndWait();
        }
    }
}
