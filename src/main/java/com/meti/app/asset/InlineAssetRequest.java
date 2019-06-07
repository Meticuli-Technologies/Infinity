package com.meti.app.asset;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/7/2019
 */
public class InlineAssetRequest implements AssetRequest {
    private static final long serialVersionUID = -3410020766809906064L;
    private final String name;

    public InlineAssetRequest(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
