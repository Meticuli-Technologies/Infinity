package com.meti.lib.asset.properties;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/7/2019
 */
public interface AssetProperties extends Serializable {
    String getName();

    Optional<String> getParentName();

    void setParentName(String value);
}
