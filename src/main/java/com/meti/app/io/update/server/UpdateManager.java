package com.meti.app.io.update.server;

import com.meti.app.io.update.Update;
import com.meti.lib.io.source.Source;

import java.util.*;

public class UpdateManager<S extends Source> {
    private final Map<S, Queue<Update>> updateMap = new HashMap<>();

    public Queue<Update> getUpdates(S source) {
        return updateMap.get(source);
    }

    public S process(S source) {
        updateMap.put(source, new LinkedList<>());
        return source;
    }

    public void update(Update update) {
        updateMap.keySet()
                .stream()
                .map(updateMap::get)
                .filter(Objects::nonNull)
                .forEach(updates -> updates.add(update));
    }
}
