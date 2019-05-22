package com.meti.lib.asset;

import com.meti.lib.source.Source;

import java.util.HashSet;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/22/2019
 */
public class AssetManager {
    private final Set<AssetTransformer> transformers = new HashSet<>();
    private final Set<Asset> assets = new HashSet<>();

    public void load(Source source){

    }
}
