package com.meti.app.text;

import com.meti.app.Controls;
import com.meti.app.InfinityController;
import com.meti.lib.asset.Asset;
import com.meti.lib.asset.text.SerializedTextAssetChange;
import com.meti.lib.asset.text.TextAsset;
import com.meti.lib.asset.text.TextAssetChange;
import com.meti.lib.net.TypeHandler;
import com.meti.lib.net.client.Client;
import com.meti.lib.net.client.handle.ResponseProcessor;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/7/2019
 */
public class TextEditorController extends InfinityController implements AssetRenderer {
    private final ResponseProcessor processor;
    @FXML
    private TextArea display;
    private TextAsset renderedAsset;

    public TextEditorController(Controls controls) {
        super(controls);
        this.processor = controls.getToolkit().getProcessor();
    }

    @Override
    public void render(Asset<?, ?> asset) {
        if(asset instanceof TextAsset){
            renderedAsset = (TextAsset) asset;
            StringBuilder builder = renderedAsset.getValue();
            display.setText(builder.toString());

            renderedAsset.onChange((textAssetChange, asset1) -> {
                try {
                    toolkit.getClient().writeAndFlush(textAssetChange);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            processor.addHandler(new TypeHandler<>(TextAssetChange.class) {
                @Override
                public Optional<Serializable> handleGeneric(TextAssetChange response, Client client) {
                    if (response.wasAdded()) {
                        int start = response.getStart();
                        display.insertText(start, String.valueOf(response.getValue()));
                    } else if (response.wasRemoved()) {
                        int start = response.getStart();
                        int stop = response.getStop();
                        display.deleteText(start, stop);
                    }
                    return Optional.empty();
                }
            });
        }
    }

    @FXML
    public void changeInput(KeyEvent event) {
        KeyCode code = event.getCode();
        if (code != KeyCode.BACK_SPACE) {
            int start = display.getCaretPosition();
            char c = code.getChar().charAt(0);
            TextAssetChange change = new SerializedTextAssetChange(start, c);
            renderedAsset.change(change);
        } else {
            int start = display.getCaretPosition();
            int stop = display.getCaretPosition() + 1;
            TextAssetChange change = new SerializedTextAssetChange(start, stop);
            renderedAsset.change(change);
        }
        //TODO: implement change display
    }
}
