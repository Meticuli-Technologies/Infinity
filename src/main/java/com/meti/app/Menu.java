package com.meti.app;

import com.meti.lib.State;
import com.meti.lib.fx.Controller;
import javafx.fxml.FXML;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/24/2019
 */
public class Menu extends Controller {
    public Menu(State state) {
        super(state);
    }

    @FXML
    public void connect() {
        try {
            ontoURL(getClass().getResource("/com/meti/app/ClientMenu.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void host() {
        try {
            ontoURL(getClass().getResource("/com/meti/app/ServerMenu.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
