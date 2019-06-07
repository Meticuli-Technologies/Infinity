package com.meti.lib.asset.text;

import com.meti.lib.asset.Asset;
import com.meti.lib.asset.properties.AssetProperties;

import java.util.function.BiConsumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public class TextAssetImpl implements TextAsset {
    private final StringBuilder value = new StringBuilder();
    private BiConsumer<TextAssetChange, Asset<TextAssetChange, StringBuilder>> onChange;
    private final AssetProperties properties;

    public TextAssetImpl(AssetProperties properties, CharSequence initialValue) {
        this.properties = properties;
        this.value.append(initialValue);
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
    public AssetProperties getProperties() {
        return properties;
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
