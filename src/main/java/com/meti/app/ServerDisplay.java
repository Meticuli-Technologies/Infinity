package com.meti.app;

import com.meti.lib.fx.BufferedConsole;
import com.meti.lib.fx.Controller;
import com.meti.lib.fx.PostInitializable;
import com.meti.lib.net.Server;
import com.meti.lib.util.Finalizable;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Supplier;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class ServerDisplay extends Controller implements Initializable, PostInitializable, Finalizable {
    @FXML
    private TextField inputField;

    @FXML
    private TextArea outputArea;

    private BufferedConsole bufferedConsole;
    private AnimationTimer timer;
    private Server server;

    @FXML
    public void checkEnter(KeyEvent event){
        if(event.getCode().equals(KeyCode.ENTER)){
            parseToken(inputField.getText());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bufferedConsole = new BufferedConsole();
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                //TODO: could be a performance hiccup, but I'm not sure until I try it
                outputArea.setText(bufferedConsole.getBuffer().toString());
            }
        };

        timer.start();
    }

    @Override
    public void finalizeController() {
        timer.stop();
    }

    private void parseToken(String text) {
        //TODO: implement ServerDisplay.parseToken(String text)
    }

    @Override
    public void postInitialize() {
        try {
            this.server = state.firstOfType(Server.class)
                    .orElseThrow((Supplier<Throwable>) () -> new IllegalStateException("Cannot find server to load in display"));
        } catch (Throwable throwable) {
            getLogger().error("", throwable);
        }
    }
}
