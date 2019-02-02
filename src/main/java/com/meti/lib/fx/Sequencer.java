package com.meti.lib.fx;

import java.util.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/2/2019
 */
public class Sequencer<T> {
    private final List<T> items = new ArrayList<>();

    public Sequencer(T... initial) {
        this(Arrays.asList(initial));
    }

    public Sequencer(Collection<T> initial) {
        items.addAll(initial);
    }

    public List<T> getItems() {
        return items;
    }

    public Optional<T> back(int index) {
        try {
            return Optional.of(items.get(index - 1));
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    public Optional<T> next(int index) {
        try {
            return Optional.of(items.get(index + 1));
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }
}