package com.meti.app;

import com.meti.lib.collect.State;
import com.meti.lib.fx.Controller;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/7/2019
 */
public class Menu extends Controller {
    public Menu(State state) {
        super(state);
    }

    @FXML
    private Text versionText;

    @FXML
    public void local(){

    }

    @FXML
    public void openSettings(){

    }
}
