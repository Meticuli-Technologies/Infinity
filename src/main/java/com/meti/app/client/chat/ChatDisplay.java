package com.meti.app.client.chat;

import com.meti.app.Controls;
import com.meti.app.client.InfinityClientController;
import com.meti.lib.net.TypeHandler;
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
public class ChatDisplay extends InfinityClientController implements Initializable {
    @FXML
    private TextArea output;

    @FXML
    private TextField input;

    public ChatDisplay(Controls controls) {
        super(controls);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        processor.addHandler(new OutputHandler());
    }

    @FXML
    public void nextInput() {
        try {
            String message = input.getText();
            client.writeAndFlush(new SerializedChatMessage(message));
            input.clear();

            processor.processNextResponse();
        } catch (Throwable throwable) {
            writeLine(throwable.getLocalizedMessage());
        }
    }

    private void writeLine(String line) {
        output.appendText(line + '\n');
    }

    private class OutputHandler extends TypeHandler<ChatMessageResponse> {
        public OutputHandler() {
            super(ChatMessageResponse.class);
        }

        @Override
        public Optional<Serializable> handleGeneric(ChatMessageResponse response, Client client) {
            writeLine(response.buildString());
            return Optional.empty();
        }
    }
}