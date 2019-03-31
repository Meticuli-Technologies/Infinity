package com.meti.app.control;

import com.meti.app.core.InfinityController;
import com.meti.lib.collection.State;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/31/2019
 */
public class Menu extends InfinityController {
    @FXML
    private Text versionText;

    public Menu(State state) {
        super(state);
    }

    @FXML
    public void connect(){

    }

    @FXML
    public void host(){

    }

    @FXML
    public void local(){
        try {
            console.log(Level.INFO, "Loading Local.");
            onto(getClass().getResource("/com/meti/app/control/Local.fxml"));
        } catch (IOException e) {
            console.log(Level.WARNING, e);
        }
    }

    @FXML
    public void settings(){

    }
}
