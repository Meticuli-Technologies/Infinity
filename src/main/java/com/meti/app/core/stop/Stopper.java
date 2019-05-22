package com.meti.app.core.stop;

import com.meti.app.core.state.StateImpl;
import com.meti.app.core.state.Toolkit;

import java.io.Closeable;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/22/2019
 */
public class Stopper implements StopperImpl {
    @Override
    public void stop(Toolkit toolkit) {
        StateImpl state = toolkit.getState();
        Set<Closeable> closeables = getCloseables(state);
        for (Closeable closeable : closeables) {
            try {
                closeable.close();
            } catch (IOException e) {
                toolkit.getLogger().log(Level.SEVERE, "Failed to close " + closeable);
            }
        }
    }

    private Set<Closeable> getCloseables(StateImpl state) {
        return state.filterByClass(Closeable.class)
                .collect(Collectors.toSet());
    }
}
