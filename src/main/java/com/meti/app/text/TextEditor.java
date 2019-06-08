package com.meti.app.text;

import com.meti.app.Controls;
import com.meti.app.client.Editor;
import com.meti.lib.asset.Asset;
import com.meti.lib.asset.properties.AssetProperties;
import com.meti.lib.javafx.InjectorLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/7/2019
 */
public class TextEditor implements Editor {
    private final Parent root;
    private final TextEditorController controller;

    public TextEditor(Controls controls) throws IOException {
        InjectorLoader loader = new InjectorLoader(List.of(controls));
        this.root = loader.load(getClass().getResource("/com/meti/app/client/text/TextEditor.fxml").openStream());
        this.controller = loader.getController();
    }

    @Override
    public boolean canRender(AssetProperties properties) {
        return properties.getName().endsWith("txt");
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public String getName() {
        return "Text";
    }

    @Override
    public void render(Asset<?, ?> asset) {
        controller.render(asset);
    }
}
