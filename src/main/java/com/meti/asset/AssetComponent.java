package com.meti.asset;

import com.meti.handle.TokenHandler;
import com.meti.net.client.ClientComponent;

import java.util.Collection;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public class AssetComponent implements ClientComponent {


    @Override
    public Collection<? extends TokenHandler> getHandlers() {
        return null;
    }
}
