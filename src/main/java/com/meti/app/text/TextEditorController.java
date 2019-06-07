package com.meti.app.text;

import com.meti.app.Controls;
import com.meti.app.InfinityController;
import com.meti.lib.asset.Asset;
import com.meti.lib.asset.text.TextAsset;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/7/2019
 */
public class TextEditorController extends InfinityController implements AssetRenderer {
    @FXML
    private TextArea input;

    public TextEditorController(Controls controls) {
        super(controls);
    }

    public void render(Asset<?, ?> asset) {
        if(asset instanceof TextAsset){
            TextAsset castedAsset = (TextAsset) asset;
            StringBuilder builder = castedAsset.getValue();
            input.setText(builder.toString());
        }
    }
}
