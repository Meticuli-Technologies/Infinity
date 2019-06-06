package com.meti.app.client;

import com.meti.app.Controls;
import com.meti.app.InfinityController;
import com.meti.lib.net.client.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.Serializable;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/1/2019
 */
public class ChatDisplay extends InfinityController implements Initializable {
    @FXML
    private TextArea output;

    @FXML
    private TextField input;

    public ChatDisplay(Controls controls) {
        super(controls);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void nextInput() {
        try {
            String message = input.getText();
            input.clear();
        } catch (Throwable throwable) {
            writeLine(throwable.getLocalizedMessage());
        }
    }

    private void writeLine(String line) {
        output.appendText(line + '\n');
    }

    private class OutputHandler extends StringTypeHandler {
        @Override
        public Optional<Serializable> handle(Object response, Client client) {
            writeLine(response.toString());
            return Optional.empty();
        }
    }
}