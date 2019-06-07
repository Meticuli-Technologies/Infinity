package com.meti.app.asset;

import com.meti.lib.asset.Asset;

import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/7/2019
 */
public interface AssetResponse extends Serializable {
    Asset<?, ?> getAsset();
}
