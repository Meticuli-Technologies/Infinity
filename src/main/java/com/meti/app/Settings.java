package com.meti.app;

import com.meti.lib.fx.Controller;
import com.meti.lib.util.Counter;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
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
        GridPane.setHalignment(name, HPos.CENTER);
        GridPane.setValignment(name, VPos.CENTER);
        contentPane.add(name, 0, counter.increment());

        TextField input = new TextField(value);
        GridPane.setHalignment(input, HPos.CENTER);
        GridPane.setValignment(input, VPos.CENTER);
        contentPane.add(input, 1, counter.get() - 1);

        GridPane.setMargin(input, new Insets(0, 25, 0, 0));

        input.setOnKeyPressed(event -> properties.setProperty(key, input.getText()));
    }
}
