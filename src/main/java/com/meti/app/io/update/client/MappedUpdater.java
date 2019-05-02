package com.meti.app.io.update.client;

import com.meti.app.io.update.Update;
import com.meti.lib.io.query.Querier;

import java.util.HashSet;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/2/2019
 */
public class MappedUpdater extends Updater {
    public final Set<UpdateHandler> handlers = new HashSet<>();

    public MappedUpdater(Querier querier) {
        super(querier);
    }

    @Override
    public void handleUpdate(Update update) {
        handlers.stream()
                .filter(handler -> handler.test(update))
                .forEach(handler -> handler.accept(update));
    }
}
