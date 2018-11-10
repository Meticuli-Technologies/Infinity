package com.meti.app;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class Menu extends Controller {
    @FXML
    public void connectToAServer(){

    }

    @FXML
    public void hostALocalServer(){
        try {
            state.firstOfType(Stage.class)
                    .orElse(new Stage())
                    .setScene(new Scene(Main.load(getClass().getResource("/com/meti/app/HostALocalServer.fxml"), state)));
        } catch (IOException e) {
            getLogger().error("", e);
        }
    }

    @FXML
    public void hostAServer(){
    }
}
