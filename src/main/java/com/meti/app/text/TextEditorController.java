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

            renderedAsset.setOnChange((change, asset1) -> {
                if (change.wasAdded()) {
                    int start = change.getStart();
                    display.insertText(start, String.valueOf(change.getValue()));
                } else if (change.wasRemoved()) {
                    int start = change.getStart();
                    int stop = change.getStop();
                    display.deleteText(start, stop);
                }
            });

            processor.addHandler(new TypeHandler<>(TextAssetChange.class) {
                @Override
                public Optional<Serializable> handleGeneric(TextAssetChange response, Client client) {
                    renderedAsset.change(response);
                    return Optional.empty();
                }
            });
        }
    }

    @FXML
    public void changeInput(KeyEvent event) {
        KeyCode code = event.getCode();
        TextAssetChange change;
        Client client = toolkit.getClient();
        System.out.println(code);
        if (code != KeyCode.BACK_SPACE) {
            int start = display.getCaretPosition();
            change = new SerializedTextAssetChange(renderedAsset.getProperties().getName(), start, event.getText());
        } else {
            int start = display.getCaretPosition();
            int stop = display.getCaretPosition() + 1;
            change = new SerializedTextAssetChange(renderedAsset.getProperties().getName(), start, stop);
        }
        try {
            client.writeAndFlush(change);
            processor.processNextResponse();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        //TODO: implement change display
    }
}
