package com.meti.lib.asset;

import java.util.Optional;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/22/2019
 */
public interface Asset {
    Optional<Asset> getParent();
    String getName();
}
