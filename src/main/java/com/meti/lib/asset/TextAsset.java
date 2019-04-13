package com.meti.lib.asset;

import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/13/2019
 */
public abstract class TextAsset extends Asset {
    private final List<String> lines;

    public TextAsset(List<String> lines) {
        this.lines = lines;
    }
}
