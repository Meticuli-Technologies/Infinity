package com.meti.lib.asset.properties;

import java.util.Optional;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/7/2019
 */
public class InlineAssetProperties implements AssetProperties {
    private final String name;
    private String parentName;

    public InlineAssetProperties(String name, String parentName) {
        this.name = name;
        this.parentName = parentName;
    }

    public InlineAssetProperties(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Optional<String> getParentName() {
        return Optional.ofNullable(parentName);
    }

    @Override
    public void setParentName(String value) {
        this.parentName = value;
    }
}
