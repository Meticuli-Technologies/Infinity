package com.meti.app.text;

import com.meti.app.Controls;
import com.meti.app.InfinityController;
import com.meti.lib.asset.Asset;
import com.meti.lib.asset.text.TextAsset;
import com.meti.lib.net.client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.awt.event.KeyEvent;

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

    @FXML
    public void changeInput(KeyEvent event){
        int start = input.getCaretPosition();
        //TODO: implement change input
    }

    @Override
    public void render(Asset<?, ?> asset) {
        if(asset instanceof TextAsset){
            TextAsset castedAsset = (TextAsset) asset;
            StringBuilder builder = castedAsset.getValue();
            input.setText(builder.toString());
        }
    }
}
