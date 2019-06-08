package com.meti.app.asset;

import com.meti.lib.asset.Asset;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/7/2019
 */
public class InlineAssetResponse implements AssetResponse {
    private static final long serialVersionUID = 1363287534760526603L;
    private final Asset<?, ?> asset;

    public InlineAssetResponse(Asset<?, ?> asset) {
        this.asset = asset;
    }

    @Override
    public Asset<?, ?> getAsset() {
        return asset;
    }
}
