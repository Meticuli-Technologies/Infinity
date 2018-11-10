package com.meti.app;

import com.meti.lib.fx.Controller;
import com.meti.lib.fx.PostInitializable;
import com.meti.lib.net.Server;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.function.Supplier;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class ServerDisplay extends Controller implements PostInitializable {
    @FXML
    private TextField inputField;

    @FXML
    private TextArea outputArea;

    private Server server;

    @FXML
    public void checkEnter(KeyEvent event){
        if(event.getCode().equals(KeyCode.ENTER)){
            parseToken(inputField.getText());
        }
    }

    private void parseToken(String text) {
        //TODO: implement ServerDisplay.parseToken(String text)
    }

    @Override
    public void postInitialize() {
        try {
            this.server = state.firstOfType(Server.class).orElseThrow((Supplier<Throwable>) () -> new IllegalStateException("Cannot find server to load in display"));
        } catch (Throwable throwable) {
            getLogger().error("", throwable);
        }
    }
}
