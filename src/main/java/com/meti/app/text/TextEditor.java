package com.meti.app.text;

import com.meti.app.client.Editor;
import com.meti.lib.asset.Asset;
import com.meti.lib.asset.properties.AssetProperties;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/7/2019
 */
public class TextEditor implements Editor {
    @Override
    public boolean canRender(AssetProperties properties) {
        return properties.getName().endsWith("txt");
    }

    @Override
    public Parent getRoot() {
        return new AnchorPane();
    }

    @Override
    public String getName() {
        return "Text";
    }

    @Override
    public void render(Asset<?, ?> asset) {
        //TODO: implement render
    }
}
