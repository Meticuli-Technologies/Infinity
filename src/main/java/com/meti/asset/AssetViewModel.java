package com.meti.asset;

import com.meti.asset.view.AssetView;
import com.meti.fx.Injector;
import com.meti.handle.TokenHandler;
import com.meti.net.client.Client;
import com.meti.net.client.ClientViewModel;
import com.meti.net.source.URLSource;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.logging.Logger;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public class AssetViewModel extends ClientViewModel {

    public AssetViewModel(Logger logger, Client client) {
        super(logger, client);
    }

    @Override
    public String getName() {
        return "Assets";
    }

    @Override
    public Collection<? extends TokenHandler> getHandlers() {
        return new ArrayList<>();
    }

    @Override
    public Parent getRoot() throws IOException {
        Injector injector = new Injector(URLSource.of("/com/meti/AssetView.fxml"), logger, client);
        Parent load = injector.load();
        return load;
    }
}
