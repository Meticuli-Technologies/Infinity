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
        buildItems(tCollection);
        return buildRoot();
    }

    private TreeItem<T> buildRoot() {
        TreeItem<T> root = new TreeItem<>();
        for (TreeItem<T> child : values()) {
            boolean isParent = true;
            for (TreeItem<T> parent : values()) {
                if (!child.equals(parent) && parent.getChildren().contains(child)) {
                    isParent = false;
                }
            }
            if (isParent) {
                root.getChildren().add(child);
            }
        }
        return root;
    }

    private Collection<TreeItem<T>> buildItems(Collection<? extends T> tCollection) {
        for (T t1 : tCollection) {
            ensureContains(t1);

            for (T t2 : tCollection) {
                if (!t1.equals(t2)) {
                    ensureContains(t2);

                    if (isParent(t1, t2) && !get(t1).getChildren().contains(get(t2))) {
                        put(t2, detectParent(t1, t2));
                        get(t1).getChildren().add(get(t2));
                    }

                    if (isParent(t2, t1) && !get(t2).getChildren().contains(get(t1))) {
                        detectParent(t2, t1);
                        get(t2).getChildren().add(get(t1));
                    }
                }
            }
        }

        return values();
    }

    private TreeItem<T> ensureContains(T value) {
        if (!containsKey(value)) {
            put(value, createTreeItem(value));
        }
        return get(value);
    }

    protected abstract TreeItem<T> detectParent(T parent, T child);

    protected abstract boolean isParent(T parent, T child);

    protected abstract TreeItem<T> createTreeItem(T value);
}
