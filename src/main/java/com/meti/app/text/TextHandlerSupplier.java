package com.meti.app.text;

import com.meti.app.Toolkit;
import com.meti.app.server.ServerHandlerSupplier;
import com.meti.lib.asset.Asset;
import com.meti.lib.asset.manage.AssetManager;
import com.meti.lib.asset.text.TextAsset;
import com.meti.lib.asset.text.TextAssetChange;
import com.meti.lib.net.TypeHandler;
import com.meti.lib.net.client.Client;
import com.meti.lib.net.client.handle.ResponseHandler;
import com.meti.lib.net.server.Server;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/8/2019
 */
public class TextHandlerSupplier implements ServerHandlerSupplier {
    @Override
    public Collection<? extends ResponseHandler> getHandlers(Toolkit toolkit) {
        AssetManager assetManager = toolkit.getAssetManager();
        Server server = toolkit.getServer();
        return Set.of(new TypeHandler<>(TextAssetChange.class) {
            @Override
            public Optional<Serializable> handleGeneric(TextAssetChange response, Client client) {
                Asset<?, ?> asset = assetManager.getAssetByName(response.getName());
                if (asset instanceof TextAsset) {
                    Asset<TextAssetChange, StringBuilder> castedAsset = (TextAsset) asset;
                    if(castedAsset.getOnChange() == null){
                        castedAsset.setOnChange((textAssetChange, textAssetChangeStringBuilderAsset) -> {
                            for (Client serverClient : server.getClients()) {
                                try {
                                    serverClient.writeAndFlush(response);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    castedAsset.change(response);
                }
                return Optional.empty();
            }
        });
    }
}
