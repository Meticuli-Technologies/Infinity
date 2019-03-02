package com.meti.app;

import com.meti.lib.State;
import com.meti.lib.fx.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/24/2019
 */
public class ServerMenu extends Controller  {
    @FXML
    private TextField portField;


    public ServerMenu(State state) {
        super(state);
    }

    @FXML
    public void back(){

    }

    @FXML
    public void next(){

    }
}
