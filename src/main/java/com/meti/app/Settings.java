package com.meti.app;

import com.meti.lib.fx.Controller;
import com.meti.lib.util.Counter;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.Properties;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/29/2019
 */
public class Settings extends Controller {
    @FXML
    private GridPane contentPane;

    private final Counter counter = new Counter();
    private Properties properties;

    @Override
    public void confirm() {
        properties = state.get().singleContent(Properties.class);
        properties.keySet().forEach(o -> {
            String key = o.toString();
            String value = properties.getProperty(key);
            handleProperty(key, value);
        });
    }

    private void handleProperty(String key, String value) {
        Text name = new Text(key);
        contentPane.add(name, 0, counter.increment());

        TextField input = new TextField(value);
        contentPane.add(input, 1, counter.get());

        input.setOnKeyPressed(event -> properties.setProperty(key, input.getText()));
    }
}
