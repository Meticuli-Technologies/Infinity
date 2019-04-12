package com.meti.app.control;

import com.meti.lib.util.State;
import javafx.fxml.FXML;

import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class Menu extends InfinityController{
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
            onto(getClass().getResource("/com/meti/Local.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
