package com.meti.app;

import com.meti.lib.fx.Controller;
import com.meti.lib.fx.ControllerLoader;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class Menu extends Controller {
    @FXML
    public void connectToAServer(){
        //TODO: connect to a server
    }

    @FXML
    public void hostALocalServer(){
        try {
            state.firstOfType(Stage.class)
                    .orElse(new Stage())
                    .setScene(new Scene(ControllerLoader.load(getClass().getResource("/com/meti/app/HostALocalServer.fxml"), state)));
        } catch (IOException e) {
            getLogger().error("", e);
        }
    }

    @FXML
    public void hostAServer(){
        //TODO: host a server
    }
}
