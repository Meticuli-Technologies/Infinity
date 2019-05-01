package com.meti.lib.io.server;

import com.meti.lib.io.source.Source;
import com.meti.lib.io.source.supplier.SourceSupplier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

public abstract class Server<S extends Source, T extends SourceSupplier<S>> {
    public final List<S> sources = new ArrayList<>();
    public final T supplier;
    protected Consumer<S> onAccept;
    private boolean listening = false;

    Server(T supplier) {
        this.supplier = supplier;
    }

    protected abstract void accept(S source) throws IOException, ClassNotFoundException;

    public ServerListener getListener() {
        if (listening) {
            throw new IllegalStateException("Server already listening");
        }
        listening = true;
        return new ServerListener();
    }

    public void setOnAccept(Consumer<S> onAccept) {
        this.onAccept = onAccept;
    }

    private class ServerListener implements Callable<Server> {
        @Override
        public Server call() throws IOException, ClassNotFoundException {
            while (supplier.isOpen()) {
                handleSource();
            }
            return Server.this;
        }

        private void handleSource() throws IOException, ClassNotFoundException {
            S source = supplier.get();
            sources.add(source);
            if (onAccept != null) {
                onAccept.accept(source);
            }
            accept(source);
        }
    }
}