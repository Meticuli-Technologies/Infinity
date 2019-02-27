package com.meti.app;

import com.meti.lib.State;
import com.meti.lib.fx.Controller;
import com.meti.lib.fx.ControllerLoader;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/24/2019
 */
public class ClientMenu extends Controller {
    @FXML
    private TextField address;

    @FXML
    private TextField port;

    public ClientMenu(State state) {
        super(state);
    }

    @FXML
    public void back(){

    }

    @FXML
    public void next(){

    }
}
