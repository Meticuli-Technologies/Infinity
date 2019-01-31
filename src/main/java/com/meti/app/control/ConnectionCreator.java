package com.meti.app.control;

import com.meti.lib.fx.FXWizard;
import com.meti.lib.net.Connection;
import com.meti.lib.state.State;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/30/2019
 */
public abstract class ConnectionCreator<C extends Connection<?, ?, ?>> extends FXWizard<C> {
    private final ObjectProperty<C> current = new SimpleObjectProperty<>();

    public ConnectionCreator(String name, State state, Parent root) {
        super(name, state, root);
    }

    public boolean reset() throws IOException {
        boolean result = false;
        if (current.get() != null) {
            current.get().close();
            result = true;
        }

        current.set(getResult());
        return result;
    }
}
