package com.meti.app;

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

    }

    @FXML
    public void hostALocalServer(){
        try {
            Stage stage = state.firstOfType(Stage.class).orElse(new Stage());
            stage.setScene(new Scene(Main.load(getClass().getResource("/com/meti/app/HostALocalServer.fxml"), state)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void hostAServer(){

    }
}
