package com.meti.app;

import com.meti.lib.State;
import com.meti.lib.fx.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.Socket;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/28/2019
 */
public class ClientDisplay extends Controller  {
    @FXML
    private TextField input;

    @FXML
    private TextArea output;

    public ClientDisplay(State state) {
        super(state);
    }

    public void load(Socket socket) {
    }

    @FXML
    public void handleInput(KeyEvent event){

    }
}
