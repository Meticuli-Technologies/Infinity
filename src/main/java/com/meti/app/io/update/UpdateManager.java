package com.meti.app.io.update;

import com.meti.lib.io.source.Source;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

public class UpdateManager<S extends Source> {
    private final Map<S, Queue<Update>> updateMap = new HashMap<>();

    public void add(Update update) {
        updateMap.keySet()
                .stream()
                .map(updateMap::get)
                .filter(Objects::nonNull)
                .forEach(updates -> updates.add(update));
    }

    public Update getMostRecentWith(S source) {
        return updateMap.get(source).poll();
    }
}
