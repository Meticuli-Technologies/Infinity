package com.meti.lib.asset;

import java.util.function.BiConsumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public class TextAsset implements Asset<TextAssetChange, StringBuilder> {
    private final StringBuilder value = new StringBuilder();
    private final String name;
    private BiConsumer<TextAssetChange, Asset<TextAssetChange, StringBuilder>> onChange;

    public TextAsset(String name, StringBuilder value) {
        this.name = name;
        this.value.append(value);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void change(TextAssetChange change) {
        if (onChange != null) {
            onChange.accept(change, this);
        }

        if (change.wasAdded()) {
            insert(change);
        } else if (change.wasRemoved()) {
            delete(change);
        }
    }

    @Override
    public StringBuilder getValue() {
        return value;
    }

    @Override
    public void onChange(BiConsumer<TextAssetChange, Asset<TextAssetChange, StringBuilder>> onChange) {
        this.onChange = onChange;
    }

    private void insert(TextAssetChange change) {
        value.insert(change.getStart(), change.getValue());
    }

    private void delete(TextAssetChange change) {
        value.delete(change.getStart(), change.getStop());
    }
}
