package com.meti.lib.asset.text;

import com.meti.lib.asset.Asset;
import com.meti.lib.asset.properties.AssetProperties;

import java.util.function.BiConsumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public class SerializedTextAsset implements TextAsset {
    private static final long serialVersionUID = 1978563885296338066L;
    private transient BiConsumer<TextAssetChange, Asset<TextAssetChange, StringBuilder>> onChange;
    private final StringBuilder value = new StringBuilder();
    private final AssetProperties properties;

    @Override
    public BiConsumer<TextAssetChange, Asset<TextAssetChange, StringBuilder>> getOnChange() {
        return onChange;
    }

    public SerializedTextAsset(AssetProperties properties, CharSequence initialValue) {
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
    public void setOnChange(BiConsumer<TextAssetChange, Asset<TextAssetChange, StringBuilder>> onChange) {
        this.onChange = onChange;
    }

    private void insert(TextAssetChange change) {
        value.insert(change.getStart(), change.getValue());
    }

    private void delete(TextAssetChange change) {
        value.delete(change.getStart(), change.getStop());
    }
}
