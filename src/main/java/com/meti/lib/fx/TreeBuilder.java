package com.meti.lib.fx;

import javafx.scene.control.TreeItem;

import java.util.Collection;
import java.util.HashMap;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/17/2018
 */
public abstract class TreeBuilder<T> extends HashMap<T, TreeItem<T>> {
    public TreeItem<T> buildTree(Collection<? extends T> tCollection) {
        for (T t1 : tCollection) {
            ensureContains(t1);

            for (T t2 : tCollection) {
                if (!t1.equals(t2)) {
                    ensureContains(t2);

                    if (isParent(t1, t2)) {
                        get(t1).getChildren().add(get(t2));
                    }

                    if (isParent(t2, t1)) {
                        get(t2).getChildren().add(get(t1));
                    }
                }
            }
        }
        return null;
    }

    public TreeItem<T> ensureContains(T value) {
        if (!containsKey(value)) {
            put(value, createTreeItem(value));
        }
        return get(value);
    }

    public abstract boolean isParent(T parent, T child);

    public abstract TreeItem<T> createTreeItem(T value);
}
