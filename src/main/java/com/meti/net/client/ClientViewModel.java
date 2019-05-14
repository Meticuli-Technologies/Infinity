package com.meti.net.client;

import com.meti.handle.TokenHandler;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Collection;
import java.util.logging.Logger;

public abstract class ClientViewModel {
    protected final Logger logger;
    protected final Client client;

    public ClientViewModel(Logger logger, Client client) {
        this.logger = logger;
        this.client = client;
    }

    public abstract String getName();

    public abstract Collection<? extends TokenHandler> getHandlers();
    public abstract Parent getRoot() throws IOException;
}
